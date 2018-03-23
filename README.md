# Conquest
Documentation: https://github.com/Fish-In-A-Suit/Conquest/wiki


## 1. Changelog
### Snapshot 0.1
Additions
  - Main class
  - Window class
  - Input class

The current program can now create a black window and respond to keyboard events

### Snapshot 0.2
Additions:
  - Renderer class
  
Changes:
  - Lambda expressions are now used to collect input events --> the input can now be collected directly inside of Window class without the need for having the Input class to set up the key callback.
  - Input class deprecated

### Snapshot 0.3 - various code corrections and improvements
Additions:
  - a keycallback instance variable of type GLFWKeyCallback to the Window class and used it inside the glfwSetKeyCallback(...). The purpose of this is to be able to free the callback in the main class, which was previously not possible due to the fact that there was no variable referring to the created key callback
  - more window hints so as to create the GLFW 3.2 context, specified to use the core functionality and set the forward compatibility of GLFW to true --> all deprecated functionalities are deactivated
  
Changes:
  - the glfwSwapBuffers(window.windowHandle) method was incorrectly placed inside the render() method of the Renderer class in the previous snapshot. In this snapshot, it is correctly placed in the update() method of the Main class. It's logical if you think about it... you should swap the buffers in the update method and not in the render method
  
Issues:
  - the game loop doesn't run at a constant rate - the values of ups and fps fluctuate randomly