package com.example.demo.controller;


import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
public class UserController {

  @Autowired
  private UserService uService;
 
       

       @PostMapping("/post")
       public String postMethodName(@RequestBody User bookUser) {
           uService.signUp(bookUser);
           return "saved";
       }
       
       @GetMapping("book/{id}")
       public User getMethodName(@PathVariable int id ) {
        return uService.getUser(id);
       
       }
      @PutMapping("book/{email}")

      public ResponseEntity<String> putMethodName(@PathVariable String email, @RequestBody User bookUser) {
          User user=uService.forgetPassword(email);
          if(user!=null){
            user.setPassword(bookUser.getPassword());
            uService.signUp(user);
          }
          else{
            return new ResponseEntity<>("data Not found",HttpStatus.NOT_ACCEPTABLE);
          }
          return new ResponseEntity<>("Updated",HttpStatus.ACCEPTED); 
      }
      @GetMapping("book/{email}/{password}")
      public String getmethod(@PathVariable String email,@PathVariable String password){
        User userr=uService.getUserr(email,password);
        if(userr!=null){
          return "SignIn Successfully";
        }
        else{
          return "email or password is invalid";
        }
        
      }
      @GetMapping("book/alluser")
      public java.util.List<User> getall(){
        return uService.getalluser();
      }
      @GetMapping("page/{pg}/{si}")
      public List<User> getMethodSort(@PathVariable("pg") int pg,@PathVariable("si") int si) {
          return uService.pagenation(pg, si);
      }
      @GetMapping("page/{pg}/{si}/{name}")
      public Page<User> getMethodName(@PathVariable("pg") int pg,@PathVariable("si") int si,@PathVariable("name") String name) {
          return uService.pageNationSort(pg, si,name);
      }
      
      @DeleteMapping("dele/{id}")
      public User delUser(@PathVariable int id){
        return uService.deleteuser(id);
      }
    
      
    //ONE TO ONE AND ONE TO MAY RELATIONSHIPS FOR ATBLE CONNECTION
       

    @PostMapping("/user")
    public ResponseEntity<User> postMethodNam(@RequestBody User a) {
       if(uService.getUser(a.getId())==null){
         return new ResponseEntity<>(uService.signUp(a),HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getMethodNam(@PathVariable int id) {
        if(uService.getUser(id)!=null){
            return new ResponseEntity<>(uService.getUser(id),HttpStatus.OK);
        }
        return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/user")
    public ResponseEntity<List<User>> getMethodName() {
        if(uService.getalluser()!=null){
            return new ResponseEntity<>(uService.getalluser(),HttpStatus.OK);
        }
        return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<User> putMethodName(@PathVariable int id, @RequestBody User a) {

        User ex=uService.getUser(id);
        if(ex!=null){
            ex.setAddress(a.getAddress());
            ex.setEmail(a.getEmail());
            return new ResponseEntity<>(uService.signUp(ex),HttpStatus.OK);
        }
        return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> delete(@PathVariable int id){
        if(uService.getUser(id)!=null){
            uService.deleteuser(id);
            return new ResponseEntity<>("Book deleted successfully",HttpStatus.OK);
        }
        return new ResponseEntity<>("Book not found with ID:"+id,HttpStatus.INTERNAL_SERVER_ERROR);
    }
       
}

