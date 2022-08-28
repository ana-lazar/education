#include "cuda_runtime.h"
#include "device_launch_parameters.h"

#include <opencv2/opencv.hpp>
#include <opencv2/imgcodecs.hpp>
#include <opencv2/highgui.hpp>

#include <stdio.h>
#include <iostream>

#define THREADS_PER_BLOCK 256
#define PATH_TO_ORIGIN_IMG "img3.jpg"
#define PATH_TO_RESULT_IMG "new_img.jpg"

#ifdef __INTELLISENSE__
#define CUDA_KERNEL(...)
#else
#define CUDA_KERNEL(...) <<< __VA_ARGS__ >>>
#endif

using namespace std;
using namespace cv;

struct pixel {
    int r, g, b;
};

void imageToVector(const Mat* img, pixel* vec)
{
    int rows = img->rows;
    int cols = img->cols;

    // convert cv::Mat pixels to structs
    int idx = 0;
    for (int i = 0; i < rows; i++)
    {
        for (int j = 0; j < cols; j++)
        {
            Vec3b px = img->at<Vec3b>(Point(j, i));
            pixel mypx;
            mypx.b = px[0];
            mypx.g = px[1];
            mypx.r = px[2];
            vec[idx++] = mypx;
        }
    }
}

Mat vectorToImage(pixel* vec, int rows, int cols)
{
    Mat result_img = Mat(rows, cols, CV_8UC3);
    int idx = 0;
    for (int i = 0; i < rows; i++)
    {
        for (int j = 0; j < cols; j++)
        {
            pixel mypx = vec[idx++];
            Vec3b px = Vec3b(mypx.b, mypx.g, mypx.r);
            result_img.at<Vec3b>(Point(j, i)) = px;
        }
    }
    return result_img;
}

// __device__ => called from GPU, runs on GPU
__device__ void applySepiaToPixel(pixel* px)
{
    // Pixel = Blue Green Red
    int tr = (int)(0.393 * px->r + 0.769 * px->g + 0.189 * px->b);
    int tg = (int)(0.349 * px->r + 0.686 * px->g + 0.168 * px->b);
    int tb = (int)(0.272 * px->r + 0.534 * px->g + 0.131 * px->b);
    if (tr > 255)
        px->r = 255;
    else
        px->r = tr;
    if (tg > 255)
        px->g = 255;
    else
        px->g = tg;
    if (tb > 255)
        px->b = 255;
    else
        px->b = tb;
}

// __global__ => called from CPU, runs on GPU
__global__ void sepiaKernel(pixel* d_img, int rows, int cols)
{
    // each thread applies sepia filter on every pixel of a row
    int tid = threadIdx.x + blockIdx.x * blockDim.x;
    int start = tid * cols;
    int total_pixels = rows * cols;

    if (start + cols < total_pixels)
        for (int y = start; y < start + cols; y++)
            applySepiaToPixel(&d_img[y]);
}

int main()
{
    pixel* h_img;    // host image
    pixel* d_img;    // device image
    int rows, cols, size;
    Mat img;

    // Read the original image on host
    char* image_path = PATH_TO_ORIGIN_IMG;
    img = imread(image_path, IMREAD_COLOR);
    if (img.empty())
    {
        cout << "Could not read the image: " << image_path << endl;
        return 1;
    }

    // Get the image dimensions
    rows = img.rows;
    cols = img.cols;
    size = rows * cols * sizeof(pixel);

    // Allocate memory on host for image
    h_img = (pixel*)malloc(size);

    // Allocate memory on device for image
    cudaMalloc((void**)&d_img, size);

    // Show the original image
    imshow("Original image", img);
    waitKey(0); // Wait for a keystroke in the window

    //Convert cv::Mat to pixel vector
    imageToVector(&img, h_img);

    // Copy input image to device
    cudaMemcpy(d_img, h_img, size, cudaMemcpyHostToDevice);
    
    // Launch sepia kernel on GPU (each CUDA thread filters one whole row of pixels)
    int required_blocks = (rows + THREADS_PER_BLOCK - 1) / THREADS_PER_BLOCK;
    sepiaKernel CUDA_KERNEL(required_blocks, THREADS_PER_BLOCK)(d_img, rows, cols);
    
    // Copy result back to host
    cudaMemcpy(h_img, d_img, size, cudaMemcpyDeviceToHost);

    // Convert pixel matrix to cv::Mat
    img = vectorToImage(h_img, rows, cols);

    // Show the filtered image
    imshow("Filtered image", img);
    waitKey(0);

    // Saving the filtered image
    imwrite(PATH_TO_RESULT_IMG, img);
    
    // Cleanup
    free(h_img);
    cudaFree(d_img);

    return 0;
}