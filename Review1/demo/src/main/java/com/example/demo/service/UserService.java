package com.example.demo.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository uRepository;

    public User signUp(User user){
        return uRepository.save(user);
    }
    public User getUser(int id){
        return uRepository.findById(id).orElse(null);
    }
    public User deleteuser(int id){
       uRepository.deleteById(id);
    return null;
    }
    public User getUserr(String email,String password){
        return uRepository.findByEmailAndPassword(email,password);
    }
    public User forgetPassword(String email) {
       
        return uRepository.findByEmail(email);
    
    }
    public List<User> getalluser(){
        return uRepository.findAll();
    }
    public List<User> pagenation(int pageNo,int size){
     
       Page<User> pg= uRepository.findAll(PageRequest.of(pageNo, size));
       return pg.getContent();
    }
    public Page<User> pageNationSort(int pageNo,int size, String name){
     
       return uRepository.findAll(PageRequest.of(pageNo, size,Sort.by(Direction.DESC,name)));
    }
   
}

