### Phase 1 Feedback

#### General Comments
- Looks like you are well on your way to getting this finished!  You will need to 
make some changes as requirements for future phases of the project are released.  

#### Checkstyle
- passes :-) 

#### Code coverage
- 100% (no problem not covering methods that consume data of type Graphics)


#### Tests
- all tests pass :-)


#### Documentation
- this is by far the weakest part of your project.  Please be sure to update
all methods (except simple getters and setters) with REQUIRES/MODIFIES/EFFECTS 
style documentation (or standard Javadoc) before submitting the next phase.


#### Implementation
- very good, just a few issues:
   - in SoundEffects, note the warnings from IntelliJ about accessing static methods 
   using an instance reference
   - avoid the use of public static fields as a way of communication between
   classes (e.g. the tetris, gameBackground and tetrisMusic fields in the Tetris 
   class).  The fact that the fields are public and static means that they are 
   accessible to your entire code base and therefore vulnerable to unintended
   changes.
   
                       