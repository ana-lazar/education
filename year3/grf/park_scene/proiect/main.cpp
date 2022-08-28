#include <glad/glad.h>
#include <GLFW/glfw3.h>
#include <iostream>
#include <fstream>
#include <stdlib.h>
#include <vector>
#include <math.h>

#include <glm/glm.hpp>
#include <glm/gtc/matrix_transform.hpp>
#include <glm/gtc/type_ptr.hpp>

#include "model.h"
#include "mesh.h"
#include "obiect.h"
#include "scena.h"

#define STB_IMAGE_IMPLEMENTATION
#include "stb_image.h"

// functii ce creaza shaderele
GLuint createProgram(char* VSFile, char* FSFile);
std::string readFile(const char *filePath);

// functie ce creaza scena
// aceasta functie trebuie rescrisa de voi
Scena createScena(GLuint programShadere);


// functii ce initializeaza fereastra si
// proceseaza intrarile de la mouse si tastatura
GLFWwindow* initializari();
void framebuffer_size_callback(GLFWwindow* window, int width, int height);
void mouse_callback(GLFWwindow* window, double xpos, double ypos);
void scroll_callback(GLFWwindow* window, double xoffset, double yoffset);
void processInput(Scena S,GLFWwindow *window);

// configurari
const unsigned int SCR_WIDTH = 1000;
const unsigned int SCR_HEIGHT = 500;

// camera
glm::vec3 cameraPos   = glm::vec3(20.0f, 2.0f, 23.0f);
glm::vec3 cameraFront = glm::vec3(0.0f, -1.0f, -1.0f);
glm::vec3 cameraUp    = glm::vec3(0.0f, 1.0f, 0.0f);
glm::vec3 aux   = glm::vec3(0.0f, 0.0f, 0.0f);

bool firstMouse = true;
float yaw   = -90.0f;	// yaw is initialized to -90.0 degrees since a yaw of 0.0 results in a direction vector pointing to the right so we initially rotate a bit to the left.
float pitch =  0.0f;
float lastX =  800.0f / 2.0;
float lastY =  600.0 / 2.0;
float fov   =  45.0f;

// timing
float deltaTime = 0.0f;	// time between current frame and last frame
float lastFrame = 0.0f;

std::vector<int> trees;

int main()
{
    // initializam fereastra
    GLFWwindow* fereastra = initializari();

    // cream shaderele si folosim programul creat
    // (avand o scena simpla folosim un singur program)
    GLuint program = createProgram("obiect1.vert","obiect1.frag");
    glUseProgram(program);

    //cream scena
    Scena S = createScena(program);

    while(!glfwWindowShouldClose(fereastra))
    {
        // per-frame time logic
        // --------------------
        float currentFrame = static_cast<float>(glfwGetTime());
        deltaTime = currentFrame - lastFrame;
        lastFrame = currentFrame;

        // trimitem matricea projection catre shadere
        glm::mat4 projection = glm::perspective(glm::radians(fov), (float)SCR_WIDTH / (float)SCR_HEIGHT, 0.1f, 100.0f);
        glUniformMatrix4fv(glGetUniformLocation(program,"projection"), 1, GL_FALSE,&projection[0][0]);

        // camera/view transformation
        glm::mat4 view = glm::lookAt(cameraPos, cameraPos + cameraFront, cameraUp);
        glUniformMatrix4fv(glGetUniformLocation(program, "view"), 1, GL_FALSE, &view[0][0]);

        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT);

        S.DrawScene();

        // procesam intrarile ferestrei: din taste si/sau mouse
        processInput(S,fereastra);

        glfwSwapBuffers(fereastra);
        glfwPollEvents();
    }
    glfwTerminate();
    return 0;
}

Scena createScena(GLuint program){

    Scena aux = Scena();

    // load models
    // -----------

    // aici aveti un exemplu extrem de sumar cum puteti incarca si folosi doua obiecte.

    locatie l = {0.0, 0.0, 0.0};
    rotatie r = {0.0, 1.0, 1.0, 1.0};
    scalare s = {1.0f, 1.0f, 1.0f};

    // cream iarba
    aux.addObiect(Obiect(0, "resurse/grass/grassy_s.obj", l, r, s, program));
    aux.addObiect(Obiect(1, "resurse/grass/grassy_d.obj", {1.0, 0.0, 0.0}, r, s, program));

    for(int i = 0; i < 40; i++){
        for(int j = 0; j < 40; j++){
            if (i+j*40 > 1){
                if (rand() % 2 == 1) {
                    aux.addObiect(Obiect(i+j*40, aux.getObiect(0), {i*1.0, 0.0, j*1.0}, r, s, program));
                }
                else {
                    aux.addObiect(Obiect(i+j*40, aux.getObiect(1), {i*1.0, 0.0, j*1.0}, r, s, program));
                }
            }
        }
    }

    // pozitionam banca
    aux.addObiect(Obiect(2001, "resurse/bench/wood-bench.obj", {20.0, 0.0, 20.0}, r, {0.01f, 0.01f, 0.01f}, program));

    // adaugam pietre
    aux.addObiect(Obiect(2002, "resurse/rock/rock.obj", {17.0, 0.5, 20.0}, r, {1.0f, 1.0f, 1.0f}, program));

    // adaugam leagan
    aux.addObiect(Obiect(2003, "resurse/swing-set/swing-set.obj", {26.0, 0.5, 25.0}, r, {0.3f, 0.3f, 0.3f}, program));

    // adaugam copaci
    aux.addObiect(Obiect(2004, "resurse/tree-un/tree-un.obj", {23.0, 0.5, 15.0}, r, {0.5f, 0.5f, 0.5f}, program));

    // adaugam tufis
    aux.addObiect(Obiect(2005, "resurse/bush/grass-bush-single.obj", {18.0, 0.5, 20.0}, r, {0.01f, 0.01f, 0.01f}, program));

    // adaugam pisica
    aux.addObiect(Obiect(2007, "resurse/cat/12221_Cat_v1_l3.obj", {25.45, 4.1, 25}, {260.0, 1.0, 0.0, 0.0}, {0.02f, 0.02f, 0.02f}, program));

    // adaugam caine
    aux.addObiect(Obiect(2011, "resurse/dog_v3/13466_Canaan_Dog_v1_L3.obj", {24.0, 0.0, 20.0}, {270.0, 1.0, 0.0, 0.0}, {0.03f, 0.03f, 0.03f}, program));

     // adaugam pasare
    aux.addObiect(Obiect(2012, "resurse/bird/12213_Bird_v1_l3.obj", {25.6, 3.93, 21.6}, {270.0, 1.0, 0.0, 0.0}, {0.1f, 0.1f, 0.1f}, program));

    // adaugam skateboard
    aux.addObiect(Obiect(2013, "resurse/skateboard/11703_skateboard_v1_L3.obj", {25.45, 3.9, 25}, {260.0, 1.0, 0.0, 0.0}, {0.02f, 0.02f, 0.02f}, program));

    // adaugam veverita
    aux.addObiect(Obiect(2008, "resurse/squirrel/10051_Squirrel_v2_L3.obj", {23.0, 2.0, 15.2}, r, {0.01f, 0.01f, 0.01f}, program));

    // adaugam minge
    aux.addObiect(Obiect(2009, "resurse/ball/10536_soccerball_V1_iterations-2.obj", {33.0, 0.25, 30.0}, r, {0.03f, 0.03f, 0.03f}, program));

    // adaugam poarta
    aux.addObiect(Obiect(2010, "resurse/football_goal/football goal.obj", {30.0, 0.8, 25.0}, {270.0, 1.0, 0.0, 0.0}, {0.01f, 0.01f, 0.01f}, program));

    // adaugam sky dome
    aux.addObiect(Obiect(2006, "resurse/skybox/skydome.obj", {20.0, 2.0, 20.0}, r, {20.0f, 20.0f, 20.0f}, program));

    for(int i = 0; i < 3; i++){
        for(int j = 0; j < 3; j++){
            if (i+j*4 > 1){
                if (i * 10 > 10 && i * 10 < 30 && j * 10 > 10 && j * 10 < 30) {
                    continue;
                }
                else if (rand() % 2 == 1) {
                    aux.addObiect(Obiect(2100+i+j*5, aux.getObiect(2004), {i*10.0, 0.0, j*10.0}, r, {0.5f, 0.5f, 0.5f}, program));
                    trees.push_back(2100+i+j*5);
                }
                else {
                    aux.addObiect(Obiect(2100+i+j*5, aux.getObiect(2004), {i*10.0, 0.0, j*10.0}, r, {0.5f, 0.5f, 0.5f}, program));
                    trees.push_back(2100+i+j*5);
                }
            }
        }
    }

    return aux;
}


GLFWwindow* initializari()
{
    /*
        functia initializeaza bibliotecile glfw si glad, creaza o fereastra si
            o ataseaza  unui context OpenGL
    */

    //initializam glfw
    glfwInit();

    glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
    glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
    glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

    //cream fereastra
    GLFWwindow * window = glfwCreateWindow(SCR_WIDTH, SCR_HEIGHT, "scena_01", NULL, NULL);
    if (window == NULL)
    {
        std::cout << "Failed to create GLFW window" << std::endl;
        glfwTerminate();
    }
    // facem ca aceasta fereastra sa fie contextul curent


    //atasam fereastra contextului opengl
    //glfwMakeContextCurrent(window);

    glfwMakeContextCurrent(window);
    glfwSetFramebufferSizeCallback(window, framebuffer_size_callback);
    glfwSetCursorPosCallback(window, mouse_callback);
    glfwSetScrollCallback(window, scroll_callback);

    // tell GLFW to capture our mouse
    glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);



    if (!gladLoadGLLoader((GLADloadproc)glfwGetProcAddress))
    {
        std::cout<<" nu s-a initializat biblioteca GLAD!";
    }


    // configure global opengl state
    // -----------------------------
    glEnable(GL_DEPTH_TEST);


    return window;

}

float round_meu(float var)
{
    float value = (int)(var * 1 + .5);
    return (float)value / 1;
}

int checkVertices(Obiect o){
    Model m = o.getModel();
    locatie l = o.getLoc();
    scalare s = o.getScalare();
    rotatie r = o.getRotatie().at(0);
    //std::vector<rotatie> r = o.getRotatie();
    //std::cout<<m.meshes.size()<<std::endl;
    for(int j=0;j<m.meshes.size();j++){
        Mesh mm = m.meshes.at(j);
        std::vector<float> ends = mm.ends;
        //std::cout<<mm.vertices.size()<<std::endl;
        //std::cout<<mm.vertices.size()<<std::endl;
        for(int i=0;i<mm.vertices.size();i++){
            if(round_meu(mm.vertices.at(i).Position[0]*s.x+l.x) == round_meu(aux[0]) && round_meu(mm.vertices.at(i).Position[1]*s.y+l.y) == round_meu(aux[1]) && round_meu(mm.vertices.at(i).Position[2]*s.z+l.z) == round_meu(aux[2])){
                //std::cout<<"DADA"<<std::endl;
                return 1;
            }
        }
    }
    return 0;

}

int checkBoundingBox(Obiect o,int sky){
    Model m = o.getModel();
    locatie l = o.getLoc();
    scalare s = o.getScalare();
    rotatie r = o.getRotatie().at(0);
    //std::vector<rotatie> r = o.getRotatie();
    //std::cout<<m.meshes.size()<<std::endl;
    for(int j=0;j<m.meshes.size();j++){
        Mesh mm = m.meshes.at(j);
        std::vector<float> ends = mm.ends;
        float angle = r.unghi*M_PI/180;
        //float x_min=ends.at(0)*s.x+l.x,x_max=ends.at(1)*s.x+l.x,y_min=ends.at(2)*s.y+l.y,y_max=ends.at(3)*s.y+l.y,z_min=ends.at(4)*s.z+l.z,z_max=ends.at(5)*s.z+l.z;
        float x_min=ends.at(0)*s.x+l.x,x_max=ends.at(1)*s.x+l.x,y_min=ends.at(2)*s.y,y_max=ends.at(3)*s.y,z_min=ends.at(4)*s.z,z_max=ends.at(5)*s.z;
        if(!sky){
            while(x_max-x_min<0.5){
                x_max+=0.1;
                x_min-=0.1;
            }
            while(y_max-y_min<0.5){
                y_max+=0.1;
                y_min-=0.1;
            }
            while(z_max-z_min<0.5){
                z_max+=0.1;
                z_min-=0.1;
            }
        }
        else{
            x_max-=1;
            x_min+=1;
            y_max-=1;
            y_min+=1;
            z_max-=1;
            z_min+=1;
        }

        if(r.unghi>0){
            float y_aux_min=1000,z_aux_min=1000,y_aux_max=-1000,z_aux_max=-1000,yy,zz;
            yy = cos(angle)*y_min - sin(angle)*z_min;
            if(yy<y_aux_min){
                y_aux_min = yy;
            }
            if(yy>y_aux_max){
                y_aux_max = yy;
            }
            yy = cos(angle)*y_min - sin(angle)*z_max;
            if(yy<y_aux_min){
                y_aux_min = yy;
            }
            if(yy>y_aux_max){
                y_aux_max = yy;
            }
            yy = cos(angle)*y_max - sin(angle)*z_min;
            if(yy<y_aux_min){
                y_aux_min = yy;
            }
            if(yy>y_aux_max){
                y_aux_max = yy;
            }
            yy = cos(angle)*y_max - sin(angle)*z_max;
            if(yy<y_aux_min){
                y_aux_min = yy;
            }
            if(yy>y_aux_max){
                y_aux_max = yy;
            }

            zz = sin(angle)*y_min + cos(angle)*z_min;
            if(zz<z_aux_min){
                z_aux_min = zz;
            }
            if(zz>z_aux_max){
                z_aux_max = zz;
            }
            zz = sin(angle)*y_min + cos(angle)*z_max;
            if(zz<z_aux_min){
                z_aux_min = zz;
            }
            if(zz>z_aux_max){
                z_aux_max = zz;
            }
            zz = sin(angle)*y_max + cos(angle)*z_min;
            if(zz<z_aux_min){
                z_aux_min = zz;
            }
            if(zz>z_aux_max){
                z_aux_max = zz;
            }
            zz = sin(angle)*y_max + cos(angle)*z_max;
            if(zz<z_aux_min){
                z_aux_min = zz;
            }
            if(zz>z_aux_max){
                z_aux_max = zz;
            }
            y_min=y_aux_min;
            y_max=y_aux_max;
            z_min=z_aux_min;
            z_max=z_aux_max;

        }
        y_min += l.y;
        y_max += l.y;
        z_min += l.z;
        z_max += l.z;


        if(aux[0]>= x_min && aux[0]<=x_max && aux[1]>= y_min && aux[1]<=y_max && aux[2]>= z_min && aux[2]<=z_max){
            return 1;
            //std::cout<<x<<std::endl;
            //std::cout<<"DADA"<<std::endl;
        }
    }
    return 0;
    //std::cout<<x<<" "<<x_min<<" "<<x_max<<" "<<y_min<<" "<<y_max<<" "<<z_min<<" "<<z_max<<std::endl;
}



// process all input: query GLFW whether relevant keys are pressed/released this frame and react accordingly
// ---------------------------------------------------------------------------------------------------------
void processInput(Scena S,GLFWwindow *window)
{
    aux = cameraPos;
    if (glfwGetKey(window, GLFW_KEY_ESCAPE) == GLFW_PRESS)
        glfwSetWindowShouldClose(window, true);

    int speed = 2.5;

    float cameraSpeed = static_cast<float>(speed * deltaTime);
    if (glfwGetKey(window, GLFW_KEY_W) == GLFW_PRESS){
        aux = aux + cameraSpeed * cameraFront;
    }
    if (glfwGetKey(window, GLFW_KEY_S) == GLFW_PRESS){
        aux = aux - cameraSpeed * cameraFront;
    }
    if (glfwGetKey(window, GLFW_KEY_A) == GLFW_PRESS){
        aux = aux - glm::normalize(glm::cross(cameraFront, cameraUp)) * cameraSpeed;
    }
    if (glfwGetKey(window, GLFW_KEY_D) == GLFW_PRESS){
        aux = aux + glm::normalize(glm::cross(cameraFront, cameraUp)) * cameraSpeed;
    }


    int collision = 0;

    if(aux[1]<0.1){
        collision = 1;
    }
    else{
    for (int x=2001;x<2014;x++){

        if(collision){
            break;
        }
        Obiect o = S.getObiect(x);
        if(x==2004 || x==2001){
            collision = checkVertices(o);
            continue;
        }

        if(x==2006){
            collision = checkBoundingBox(o,1);
            if(collision){
                collision = 0;
            }
            else{
                collision = 1;
            }
            continue;
        }
        collision = checkBoundingBox(o,0);
    }
    int inside_tree = 0;
    for(int i=0;i<trees.size();i++){
        if(collision){
            break;
        }
        Obiect o = S.getObiect(trees.at(i));
        inside_tree = checkBoundingBox(o,0);

        if(inside_tree){
            collision = checkVertices(o);
        }

        inside_tree = 0;
    }

    }

    if(!collision){
        cameraPos = aux;
    }
    //cameraPos = aux;
    //std::cout<<"GATA"<<std::endl;

    //std::cout<<cameraPos[0]<<" "<<cameraPos[1]<<" "<<cameraPos[2]<<std::endl;
}

// glfw: whenever the window size changed (by OS or user resize) this callback function executes
// ---------------------------------------------------------------------------------------------
void framebuffer_size_callback(GLFWwindow* window, int width, int height)
{
    // make sure the viewport matches the new window dimensions; note that width and
    // height will be significantly larger than specified on retina displays.
    glViewport(0, 0, width, height);
}

// glfw: whenever the mouse moves, this callback is called
// -------------------------------------------------------
void mouse_callback(GLFWwindow* window, double xposIn, double yposIn)
{
    float xpos = static_cast<float>(xposIn);
    float ypos = static_cast<float>(yposIn);

    if (firstMouse)
    {
        lastX = xpos;
        lastY = ypos;
        firstMouse = false;
    }

    float xoffset = xpos - lastX;
    float yoffset = lastY - ypos; // reversed since y-coordinates go from bottom to top
    lastX = xpos;
    lastY = ypos;

    float sensitivity = 0.1f; // change this value to your liking
    xoffset *= sensitivity;
    yoffset *= sensitivity;

    yaw += xoffset;
    pitch += yoffset;

    // make sure that when pitch is out of bounds, screen doesn't get flipped
    if (pitch > 89.0f)
        pitch = 89.0f;
    if (pitch < -89.0f)
        pitch = -89.0f;

    glm::vec3 front;
    front.x = cos(glm::radians(yaw)) * cos(glm::radians(pitch));
    front.y = sin(glm::radians(pitch));
    front.z = sin(glm::radians(yaw)) * cos(glm::radians(pitch));
    cameraFront = glm::normalize(front);
}

// glfw: whenever the mouse scroll wheel scrolls, this callback is called
// ----------------------------------------------------------------------
void scroll_callback(GLFWwindow* window, double xoffset, double yoffset)
{
    fov -= (float)yoffset;
    if (fov < 1.0f)
        fov = 1.0f;
    if (fov > 45.0f)
        fov = 45.0f;
}


GLuint createProgram(char* VSFile, char* FSFile)
{

    /*
        functia creaza un program shader folosind ca fisiere sursa VSFile si FSFile
        IN: numele fisierelor sursa
        OUT: aliasul programului shader
    */

    GLuint vertexShader, fragmentShader, shaderProgram;
    int success;
    char infoLog[512];


    vertexShader = glCreateShader(GL_VERTEX_SHADER);

    if( 0 == vertexShader )
    {
        std::cout << "Error creating vertex shader." << std::endl;
        exit(1);
    }

    std::string shaderCode = readFile(VSFile);
    const char *codeArray = shaderCode.c_str();
    glShaderSource( vertexShader, 1, &codeArray, NULL );

    glCompileShader(vertexShader);

    // verficam daca s-a reusit compilarea codului

    glGetShaderiv(vertexShader, GL_COMPILE_STATUS, &success);
    if (!success)
    {
        glGetShaderInfoLog(vertexShader, 512, NULL, infoLog);
        std::cout << "ERROR::SHADER::VERTEX::COMPILATION_FAILED\n" << infoLog << std::endl;
    }

    fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
    if( 0 == fragmentShader )
    {
        std::cout << "Error creating fragment shader." << std::endl;
        exit(1);
    }


    shaderCode = readFile(FSFile);
    codeArray = shaderCode.c_str();
    glShaderSource( fragmentShader, 1, &codeArray, NULL );


    glCompileShader(fragmentShader);

    // se verifica compilarea codului

    glGetShaderiv(fragmentShader, GL_COMPILE_STATUS, &success);
    if (!success)
    {
        glGetShaderInfoLog(fragmentShader, 512, NULL, infoLog);
        std::cout << "ERROR::SHADER::FRAGMENT::COMPILATION_FAILED\n" << infoLog << std::endl;
    }

    //cream programul

    shaderProgram = glCreateProgram();
    glAttachShader(shaderProgram, vertexShader);
    glAttachShader(shaderProgram, fragmentShader);
    glLinkProgram(shaderProgram);

    // se verifica procesul de link

    glGetProgramiv(shaderProgram, GL_LINK_STATUS, &success);
    if (!success)
    {
        glGetProgramInfoLog(shaderProgram, 512, NULL, infoLog);
        std::cout << "ERROR::SHADER::PROGRAM::LINKING_FAILED\n" << infoLog << std::endl;
    }
    glDeleteShader(vertexShader);
    glDeleteShader(fragmentShader);

    return shaderProgram;
}


std::string readFile(const char *filePath)
{
    std::string content;
    std::ifstream fileStream(filePath, std::ios::in);

    if(!fileStream.is_open())
    {
        std::cerr << "Could not read file " << filePath;
        std::cerr << ". File does not exist." << std::endl;
        return "";
    }

    std::string line = "";
    while(!fileStream.eof())
    {
        std::getline(fileStream, line);
        content.append(line + "\n");
    }

    fileStream.close();
    return content;
}
