#include "glad.h"
#include <GLFW/glfw3.h>

#include <glm/glm.hpp>
#include <glm/gtc/matrix_transform.hpp>
#include <glm/gtc/type_ptr.hpp>

#include "filesystem.h"
#include "shader_m.h"
#include "camera.h"
#include "model.h"

#include <iostream>

void framebuffer_size_callback(GLFWwindow* window, int width, int height);
void processInput(GLFWwindow *window);
Shader lightShaderInit(Shader tempShader);

// settings
const unsigned int SCR_WIDTH = 1560;
const unsigned int SCR_HEIGHT = 1000;

// camera
Camera camera(glm::vec3(0.0f, 0.0f, 15.0f));
float lastX = SCR_WIDTH / 2.0f;
float lastY = SCR_HEIGHT / 2.0f;

auto sun_model = glm::mat4(1.0f);
auto earth_model = glm::mat4(1.0f);
auto moon_model = glm::mat4(1.0f);

//scale
auto sun_scale = glm::vec3(0.005f, 0.005f, 0.005f);
auto earth_scale = glm::vec3(0.001f, 0.001f,0.001f);
auto moon_scale = glm::vec3(0.05f, 0.05f, 0.05f);

//rotation
auto earth_rotation_axes = glm::vec3(0.0f, 1.0f, 0.0f);

// timing
float deltaTime = 0.0f;
float lastFrame = 0.0f;

glm::vec3  lightPos(0.0f, 0.0f, 0.0f);

int main()
{
    // glfw: initialize and configure
    // ------------------------------
    glfwInit();
    glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
    glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
    glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

#ifdef __APPLE__
    glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE); // uncomment this statement to fix compilation on OS X
#endif

    // glfw window creation
    // --------------------
    GLFWwindow* window = glfwCreateWindow(SCR_WIDTH, SCR_HEIGHT, "3 planete", NULL, NULL);
    if (window == NULL)
    {
        std::cout << "Failed to create GLFW window" << std::endl;
        glfwTerminate();
        return -1;
    }
    glfwMakeContextCurrent(window);
    glfwSetFramebufferSizeCallback(window, framebuffer_size_callback);

    // glad: load all OpenGL function pointers
    // ---------------------------------------
    if (!gladLoadGLLoader((GLADloadproc)glfwGetProcAddress))
    {
        std::cout << "Failed to initialize GLAD" << std::endl;
        return -1;
    }

    // configure global opengl state
    // -----------------------------
    glEnable(GL_DEPTH_TEST);

    // build and compile shaders
    // -------------------------
    Shader planetShader("calota.vert", "basic.frag");
    Shader lightSunShader("calota.vert", "basic.frag");

    // load models
    // -----------
    Model sunModel(FileSystem::getPath("resources\\objects\\sun\\13913_Sun_v2_l3.obj"));
    Model earthModel(FileSystem::getPath("resources\\objects\\earth\\earth.obj"));
    Model moonModel(FileSystem::getPath("resources\\objects\\planet\\planet.obj"));

    // draw in wireframe
    //glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);

    float earth_radius = 8.0f;
    float moon_radius = 0.8f;
    float earthX, earthZ;
    float moonX, moonZ;
    float startTime = glfwGetTime();
    float currentFrame;
    glm::mat4 projection, view;
    glm::vec3 tempVec;


    // render loop
    // -----------
    while (!glfwWindowShouldClose(window))
    {
        // per-frame time logic
        // --------------------
        currentFrame = glfwGetTime();
        deltaTime = currentFrame - lastFrame;
        lastFrame = currentFrame;

        // input
        // -----
        processInput(window);
        //glfwSetKeyCallback(window, key_callback);

        // render
        // ------
        glClearColor(0.05f, 0.05f, 0.05f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        //enable and init shader of planets before setting uniforms
        //planets shaders needs to reflect (and affected by) light
        planetShader = lightShaderInit(planetShader);

        // view/projection transformations
        projection = glm::perspective(glm::radians(camera.Zoom), (float)SCR_WIDTH / (float)SCR_HEIGHT, 0.1f, 100.0f);
        view = camera.GetViewMatrix();
        planetShader.setMat4("projection", projection);
        planetShader.setMat4("view", view);

        startTime += 0.0001;

        // render the sun model
        lightSunShader.use();
        lightSunShader.setMat4("projection", projection);
        lightSunShader.setMat4("view", view);
        sun_model = glm::mat4(1.0f);
        sun_model = glm::translate(sun_model, lightPos); // translate it down so it's at the center of the scene
        sun_model = glm::rotate(sun_model, (float) startTime * 30, earth_rotation_axes);
        sun_model = glm::scale(sun_model, sun_scale);	// it's a bit too big for our scene, so scale it down
        lightSunShader.setMat4("model", sun_model);
        sunModel.Draw(lightSunShader);

        planetShader.use();

        // render the earth model
        earth_model = glm::mat4(1.0f);
        earthX = sin(startTime) * earth_radius;
        earthZ = cos(startTime) * earth_radius;
        earth_model = glm::translate(earth_model, glm::vec3(earthX, 0.0f, earthZ));
        earth_model = glm::rotate(earth_model, (float) startTime * 30, earth_rotation_axes);
        earth_model = glm::scale(earth_model, earth_scale);    // it's a bit too big for our scene, so scale it down
        planetShader.setMat4("model", earth_model);

        earthModel.Draw(planetShader);

        // render the moon model
        moon_model = glm::mat4(1.0f);
        moonX = sin(startTime * 10)  * moon_radius;
        moonZ = cos(startTime * 10) * moon_radius;
        moon_model = glm::translate(moon_model, glm::vec3(moonX, 0.0f, moonZ));
        moon_model = glm::translate(moon_model, glm::vec3(earthX, 0.0f, earthZ)); // translate it down so it's at the center of the scene
        moon_model = glm::rotate(moon_model, (float) startTime * 30, earth_rotation_axes);
        planetShader.setMat4("view", view);
        moon_model = glm::scale(moon_model,moon_scale);    // it's a bit too big for our scene, so scale it down
        planetShader.setMat4("model", moon_model);
        moonModel.Draw(planetShader);

        // glfw: swap buffers and poll IO events (keys pressed/released, mouse moved etc.)
        // -------------------------------------------------------------------------------
        glfwSwapBuffers(window);
        glfwPollEvents();
    }

    // glfw: terminate, clearing all previously allocated GLFW resources.
    // ------------------------------------------------------------------
    glfwTerminate();
    return 0;
}

void processInput(GLFWwindow *window)
{
    if (glfwGetKey(window, GLFW_KEY_ESCAPE) == GLFW_PRESS)
        glfwSetWindowShouldClose(window, true);
}

void framebuffer_size_callback(GLFWwindow* window, int width, int height)
{
    // make sure the viewport matches the new window dimensions; note that width and
    // height will be significantly larger than specified on retina displays.
    glViewport(0, 0, width, height);
}

Shader lightShaderInit(Shader tempShader){

    //enable shader before setting uniforms
    tempShader.use();
    tempShader.setVec3("light.position", lightPos);
    tempShader.setVec3("viewPos", camera.Position);

    // light properties
    //values that fit better to our model
    tempShader.setVec3("light.ambient", 0.1f, 0.1f, 0.1f);
    tempShader.setVec3("light.diffuse", 1.0f, 1.0f, 1.0f);
    tempShader.setVec3("light.specular", 0.25f, 0.25f, 0.25f);

    //those values are from openGl tutorial for point lightning
    tempShader.setFloat("light.constant", 1.0f);
    tempShader.setFloat("light.linear", 0.0014f);
    tempShader.setFloat("light.quadratic", 0.000007f);

    // material properties
    tempShader.setFloat("material.shininess", 25.0f);

    return tempShader;
}
