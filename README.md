# Conquest
Documentation: https://github.com/Fish-In-A-Suit/Conquest/wiki


# 1. Changelog
## Snapshot 0.1
**Additions:**
  - Main class
  - Window class
  - Input class

The current program can now create a black window and respond to keyboard events

## Snapshot 0.2
**Additions:**
  - Renderer class
  
**Changes:**
  - Lambda expressions are now used to collect input events --> the input can now be collected directly inside of Window class without the need for having the Input class to set up the key callback.
  - Input class deprecated

## Snapshot 0.3 - various code corrections and improvements
**Additions:**
  - a keycallback instance variable of type GLFWKeyCallback to the Window class and used it inside the glfwSetKeyCallback(...). The purpose of this is to be able to free the callback in the main class, which was previously not possible due to the fact that there was no variable referring to the created key callback
  - more window hints so as to create the GLFW 3.2 context, specified to use the core functionality and set the forward compatibility of GLFW to true --> all deprecated functionalities are deactivated
  
**Changes:**
  - the glfwSwapBuffers(window.windowHandle) method was incorrectly placed inside the render() method of the Renderer class in the previous snapshot. In this snapshot, it is correctly placed in the update() method of the Main class. It's logical if you think about it... you should swap the buffers in the update method and not in the render method
  
**Issues:**
  - the game loop doesn't run at a constant rate - the values of ups and fps fluctuate randomly
  
## Snapshot 0.4: accomplished to draw two black triangles to form a quad on a white window background
  **Additions:**
    - added Mesh class inside renderEngine package. It's main responsibility is to collect 3D vertices, store them in VRAM and provide a way to acces them through an instance of itself (this instance of Mesh class will be used by Renderer class to render the vertices referenced by the mesh instance onto the window)
    - added Renderer class, which a) manages window resize events; b) draws the provided vertices using information provided by a mesh instance which is passed to it's render(Window window, Mesh mesh) method.
  - set up a window resize callback (dynamically changes the width and height fields of the Window class if a window is resized), using glfwSetFramebufferSizeCallback.
  - added a temporary runAssertions() method, whose job is just for debugging purposes - printing out various data to the console, so it can be seen whether the code behaves as it should. This method will be later removed and substituted by a whole class, whose role will be similar to the one of runAssertions() method

## Snapshot 0.5: added indexed rendering
**Additions:**
  - added two methods to the Mesh class: a) void bindIndicesBuffer(int[] indices), which loads up the indices of a model into the indices vbo;  b) IntBuffer storeDataInIntBuffer(int[] data), which stores an array of indices into an IntArray (which is then bound inside the indices vbo.
  -   - added a temporary array of ints called "indices" to the Main class

**Changes:**
  - changed the constructor for Mesh type from Mesh(float[] vertices) to Mesh(float[] vertices, int[] indices), so that array of indices can be sent to the Mesh type, which is then passed to the bindIndicesBuffer(int[] indices) method... changed the signatures of corresponding methods to match the change in the constructor of the Mesh class
  - vertexCount of the model now equals the length of the indices array
  - changed the "drawing" method from glDrawArrays(GL_TRIANGLES, 0, mesh.getVertexCount()); to glDrawElements(GL_TRIANGLES, mesh.getVertexCount(), GL_UNSIGNED_INT, 0);

### Snapshot 0.5. 1.: indexed rendering touch-ups
**Changes:**
  - indicesVboID now gets unbound and deleted in the cleanUp() method of the Mesh class
  - now binds the indicesVbo (glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, mesh.getIndicesVboID());) prior to issuing the call to glDrawElements() in the Renderer class

### Snapshot 0.5.2: code improvements
**Additions:**
  - added Timer class which deals with time measurements
  - added BufferUtilities class, which enables you to create various types of buffers with only one line of code, instead of manually specifying the buffer creation process each time (which takes about 3-4 lines of code)

**Changes:**
  - restructured the run() method of the Main class, so the game loop now has a separate method (gameLoop()).
  - better code documentation of certain parts of the source code

## Snapshot 0.6: added shader support
**Additions**:
  - created a new package called shaders, which contains ShaderProgram.java, fragmentShader.fs and vertexShader.vs
  - ShaderProgram class is responsible for performing different shader operations - creating them, attaching and linkking them  to the program, checking the success of shader creation and compilation and providing methods for binding shaders
  - vertexShader.vs represents the vertex shader
  - fragmentShader.fs represents the fragment shader
  - addedFileUtilities class inside the utils package, which is responsible for performing various operations regarding file management. Currently, it only performs reading proper file paths for use with shaders
  - added init() method to the Renderer class, which creates new shader objects and links them. This method should be called from the init() method of the Main class
  - added runAssertions() method to the Renderer class, which checks for the value of ID. This method should be called by the runAssertions() method of the Main class.
  
## snapshot 0.6. 1: improved debugging info and provided model colour
**Additions**:
  - added a float array of colours to the Main class for testing purposes
  - added a colourVboID in Mesh, which is a vertex buffer object that stored the contents of colourBuffer, which stores the contents of the colours array, which is passed as a parameter to the constructor for the Mesh object
  - added a setupColourVbo method in Mesh, which sets up a colour buffer object for a Mesh instance and binds it to an active vao of that Mesh instance
  - added layout qualifiers inside shaders - no need to specify input/output locations via method glBindAttribLocation or glFragDataLocation inside the "main engine"

**Changes:**
  - improved debugging info being printed to console
  - inside Main class, the input is now collected and responded to by a new method processInput(). That means that glfwPollEvents() has been removed from the update() method. Inside the game loop, processInput() is called prior to update().
  - changed the vertex shader to be a pass-through for colour --> now, it takes in an array of colour values for vertices and sends them off to the fragment shader (prior, it would just take position and assign colours based on position values +- 0.5)
  
## snapshot 0.7: Implemented the math library and put it to use
**Main goal:** implement a a math package and define uniform variables. Use the matrices to move the quad around.

**Description:**
  A coloured squad (made of two triangles) is rendered onto the window. It can be moved using W, A, S, D, SPACE or LSHIFT. Depending on where it is moved, it will be projected correctly using a perspective projection matrix.

- Video: https://www.youtube.com/watch?v=mqiH0ZSkM9I

**Additions:**
  - Added a private displayUpsAndFps(int ups, int fps) method inside Main class, which displays updates per second and frames per second near the window title
  - Created a whole new Math package to handle math-related operations
    - Added Matrix4f class. It represents a 4x4 matrix. It presents fucntionality to create translation and perspective projection matrices. Debugging methods for this class have also been added, which provide matrix-matrix and vector-matrix multiplication and a way to display matrices with a toString() method.
    - Added Vector3f class. It represents a 3 dimensional vector.
    - Added Vector4f class. It represents a 4 dimensional (homogenous) vector.
    - Added a class called Transformations. It uses the functionality of the above classes to construct various kinds of transformation matrices. Translation and perspective projection transformations are currently supported.
  - Added uniform variables to the vertex shader to send data such as matrices to shaders. Currently accepts a translation and projection matrix
  - Created a new package called models, which handles all of the models of the game. 
    - Created a GameEntity class. This class has references to a model's position, rotation, scale and state.
  - Added a class called Game inside the main package, which provides all game logic and makes the computer game really look like a game. Currently it holds information such as an array of GameEntity object and meshes (in the future the mesh will be inseparately bound to a given GameEntity object). This class also provides functionality for collecting and responding to user input (keys W, A, S, D, SPACE and LSHIFT).

**Changes:**
  - Changed the utility to read files from Scanner to BufferedReader (section FileUtilities.loadResource), as Scanner was too slow to read them.
  - Changed the rendering loop (renderEngine.Renderer.render) to handle the new additions. A for loop has been added to draw each entity in the array of GameEntities one by one.

**Issues:**
  - If the window is resized, the portion of the window on which rendering occurs isn't adjusted. Does it have to do with viewport of the window or the projection matrix?

### Snapshot 0.7.1: Code cleanup and viewport issue fixed

**Goal:** Code cleanup and improve the viewport issue

**Additions:**
  - added temporary class Quad to represent a Quadrant, which currently holds vertex positions, indices and colours of the quad.
  - [Main.setWindowTitle]: extended this method to take a GameEntity and display its position in the title. This method is not perfect, however. The value of fps and ups should be set inside if (System.currentTimeMillis() - timer > 1000) clause in the main loop. The position of the game entity, however, should be set outside of this if statement inside the main loop. Because the entity position is displayed so slowly, its values don't change linearly (they hop, first 1, then 5, then 9 etc). It will do for now, however, since font rendering is implemented to display this information on the screen

**Changes:**
  - The viewport issue has been resolved. The Window.resized field was always false, since the isResized() method of Window class returned false. Always. Therefore the glViewport method inside Renderer.render couldn't be reached and the viewport was never resized. Fixed the isResized() method to return the value of field Window.resized and now everything works perfectly
  - GameEntity is now improved. When passing vertex positions, indices and colours to its constructor, a brand new game entity is created from scratch based on the data provided. For example, a new GameEntity called entity is created in the constructor for Game class, which creates entity based on the data provided by a Quad instance (called quad). After that, all updates (changing position, rotation, etc) are performed on the instance of GameEntity
  
### Snapshot 0.7.2: Implement the rotation matrix

**Goal:** Implement the rotation matrix and make the triangle rotate

**Additions:**
  - Added rotateX(double angle), rotateY(double angle), rotateZ(double angle) and rotate(double rotx, double roty, double rotz) methods inside Matrix4f class. Each of the first three methods creates a corresponding rotation matrix, while the last (general) roation method uses the first three methods to create a rotation matrix which is a combination of rotations in all axes
  - Added getRotationMatrix(float rotx, float roty, float rotz) method to Transformations class, which uses the rotate method of Matrix4f class to create and return a rotation matrix
  - Added rotationMatrix uniform variable and defined the mapping to it from the Java code (more precisely from the Renderer class)
  - Pressing Y rotates the model around the y axis, pressing X rotates the model around the x axis and pressing Z rotates the model around the z axis. Pressing I rotates the model in all three axes simultaneously

**Issues:**
  - I believe the rotation around the x axis isn't working. Something has to be wrong with matrix multiplication or the like
  
### Snapshot 0.7.3: Fixed rotation around X, Y and Z axes

**Changes**:
  - Changed the way how rotation matrix works. I've created a new method (rotateXYZ) which performs correct rotation. I've left the old methods for rotation in place, which will be fixed in some future update.

### snapshot 0. 7. 4: Implemented scaling transformation

**Goal**: Fix the method for matrix-matrix multiplication and implemnent the scaling matrix

**Additions:**
  - Added a getModelMatrix(Matrix4f translation, Matrix4f rotation) method, which creates the model/world matrix (the matrix which transforms model's vertices from the model's position to the world). Currently, only translation and rotation transformations apply, scaling will be added shortly.
  - Implemented scaling effects, which can be achieved by calling transformation.getScaleMatrix(float scale) method. This method is put to use inside the Renderer.render method, where the scale of the entity is updated once per render call. The result obtained by transformation.getScaleMatrix(entity.getScale()) is then used to create the model matrix (along with translation and rotation), which is then sent to the vertex shader.

**Changes:**
  - Fixed the method multiplyByMatrix(Matrix4f mat) in class Matrix4f to produce correct results.
  - The vertexShader now only takes in mat4 modelMatrix as a parameter instead of three mat4s for translation, rotation and scale, so as to save up on some gpu power. The model matrix is calculated on the processor.

### snapshot 0.8: Implemented the textures, viewing matrix and mouse input

**Additions**:
  - Camera class, which holds fields position and rotation. These fields are used to construct the viewing matrix, which translates all of the scene negative relative to the translation of the "camera": If W is pressed, "camera" is translated for -1.0f in the Z axis direction. The scene, is however moved towards the 1.0f in the Z axis, as the camera position is negated prior to being used to construct the viewing matrix. The rotation field of camera class is used without changed by the viewing matrix.
  - viewMatrix uniform variable in the vertexShader.vs and defined a path to it from the Java code
  - getViewMatrix(Camera camera) in Transformations.java which constructs the view matrix
  - a cube which can be moved around (represented in the Cube class, it's temporary)
  - added a constructor to the Mesh class which accepts a float array of vertices, indices, texture coordinates and a Texture object
  - added a constructor to the GameEntity class which accepts a float array of vertices, indices, texture coordinates and a String representing the texture file location (out of which the texture will be made)
  - added left mouse button input
  - added scroll wheel input --> zoom in/out
  - made it possible to use textures
    - created a Texture class which can load a texture and stores its id. This is used by the GameEntity constructor, which takes in a texture path and creates a new Texture for that model (denoted by a variable modelTexture). GameEntity in turn sends that Texture object to Mesh to create it's complete textured Mesh object
     - textured Mesh instances (instances of Mesh which have a texture) have vertex coordinates stored at the 0th index of the vao and texture coordinates stored at the 1st index of the vao
  - created a resources folder to store all game-related assets
    - created subfolder textures, where the textures for the game are stored as images (relative location: resources/textures/FILENAME.png)

**Changes**:
  - Changed the way how rotations work. Previously, the scene would be rotated if the mouse moved, which was performed by using a cursor callback (this is the "gaming" style of rotation). This is, however, unconvenient for development stages, since it makes rotating around the world feel quite clumsy. Therefore, now one rotates the world around by holding left click at the same time while moving the mouse. The first method of rotation will be introduced later.
  - mesh.render() now uses method glActiveTexture and glBindTexture so that it caters for texture rendering
  - removed Quad class 
  - modified the vertex shader (currently a pass-through for vec2 textureCoordinate at location 1 of the vao, where texture coordinates are stored)
