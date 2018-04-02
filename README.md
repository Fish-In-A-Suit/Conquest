# Conquest
Documentation: https://github.com/Fish-In-A-Suit/Conquest/wiki


# 1. Changelog
## Snapshot 0.1
Additions
  - Main class
  - Window class
  - Input class

The current program can now create a black window and respond to keyboard events

## Snapshot 0.2
Additions:
  - Renderer class
  
Changes:
  - Lambda expressions are now used to collect input events --> the input can now be collected directly inside of Window class without the need for having the Input class to set up the key callback.
  - Input class deprecated

## Snapshot 0.3 - various code corrections and improvements
Additions:
  - a keycallback instance variable of type GLFWKeyCallback to the Window class and used it inside the glfwSetKeyCallback(...). The purpose of this is to be able to free the callback in the main class, which was previously not possible due to the fact that there was no variable referring to the created key callback
  - more window hints so as to create the GLFW 3.2 context, specified to use the core functionality and set the forward compatibility of GLFW to true --> all deprecated functionalities are deactivated
  
Changes:
  - the glfwSwapBuffers(window.windowHandle) method was incorrectly placed inside the render() method of the Renderer class in the previous snapshot. In this snapshot, it is correctly placed in the update() method of the Main class. It's logical if you think about it... you should swap the buffers in the update method and not in the render method
  
Issues:
  - the game loop doesn't run at a constant rate - the values of ups and fps fluctuate randomly
  
## Snapshot 0.4: accomplished to draw two black triangles to form a quad on a white window background
  Additions:
    - added Mesh class inside renderEngine package. It's main responsibility is to collect 3D vertices, store them in VRAM and provide a way to acces them through an instance of itself (this instance of Mesh class will be used by Renderer class to render the vertices referenced by the mesh instance onto the window)
    - added Renderer class, which a) manages window resize events; b) draws the provided vertices using information provided by a mesh instance which is passed to it's render(Window window, Mesh mesh) method.
  - set up a window resize callback (dynamically changes the width and height fields of the Window class if a window is resized), using glfwSetFramebufferSizeCallback.
  - added a temporary runAssertions() method, whose job is just for debugging purposes - printing out various data to the console, so it can be seen whether the code behaves as it should. This method will be later removed and substituted by a whole class, whose role will be similar to the one of runAssertions() method

## Snapshot 0.5: added indexed rendering
Additions:
  - added two methods to the Mesh class: a) void bindIndicesBuffer(int[] indices), which loads up the indices of a model into the indices vbo;  b) IntBuffer storeDataInIntBuffer(int[] data), which stores an array of indices into an IntArray (which is then bound inside the indices vbo.
  -   - added a temporary array of ints called "indices" to the Main class

Changes:
  - changed the constructor for Mesh type from Mesh(float[] vertices) to Mesh(float[] vertices, int[] indices), so that array of indices can be sent to the Mesh type, which is then passed to the bindIndicesBuffer(int[] indices) method... changed the signatures of corresponding methods to match the change in the constructor of the Mesh class
  - vertexCount of the model now equals the length of the indices array
  - changed the "drawing" method from glDrawArrays(GL_TRIANGLES, 0, mesh.getVertexCount()); to glDrawElements(GL_TRIANGLES, mesh.getVertexCount(), GL_UNSIGNED_INT, 0);

### Snapshot 0.5. 1.: indexed rendering touch-ups
Changes:
  - indicesVboID now gets unbound and deleted in the cleanUp() method of the Mesh class
  - now binds the indicesVbo (glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, mesh.getIndicesVboID());) prior to issuing the call to glDrawElements() in the Renderer class

### Snapshot 0.5.2: code improvements
Additions:
    - added Timer class which deals with time measurements
    - added BufferUtilities class, which enables you to create various types of buffers with only one line of code, instead of manually specifying the buffer creation process each time (which takes about 3-4 lines of code)
Changes:
    - restructured the run() method of the Main class, so the game loop now has a separate method (gameLoop()).
    - better code documentation of certain parts of the source code

## Snapshot 0.6: added shader support
Additions:
  - created a new package called shaders, which contains ShaderProgram.java, fragmentShader.fs and vertexShader.vs
  - ShaderProgram class is responsible for performing different shader operations - creating them, attaching and linkking them  to the program, checking the success of shader creation and compilation and providing methods for binding shaders
  - vertexShader.vs represents the vertex shader
  - fragmentShader.fs represents the fragment shader
  - addedFileUtilities class inside the utils package, which is responsible for performing various operations regarding file management. Currently, it only performs reading proper file paths for use with shaders
  - added init() method to the Renderer class, which creates new shader objects and links them. This method should be called from the init() method of the Main class
  - added runAssertions() method to the Renderer class, which checks for the value of ID. This method should be called by the runAssertions() method of the Main class.
