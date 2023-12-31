package com.avanade.invoice.services;

import com.avanade.invoice.entities.User;
import com.avanade.invoice.exceptions.ResourceNotFoundException;
import com.avanade.invoice.payloads.request.RequestUser;
import com.avanade.invoice.payloads.response.ResponseUser;
import com.avanade.invoice.repositories.UserRepository;
import com.avanade.invoice.exceptions.DatabaseException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<ResponseUser> findAll() {
        return repository.findAll().stream()
                .map(User::toResponse)
                .toList();
    }

    public ResponseUser findById(UUID id) {
        User user = findOrFailById(id);
        return user.toResponse();
    }

    public User findOrFailById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User could not be found"));
    }

    public ResponseUser create(RequestUser request) {
        User user = new User(request);
        saveOrFail(user);
        return user.toResponse();
    }

    public ResponseUser update(UUID id, RequestUser request) {
        User user = findOrFailById(id);
        user.setName(request.name());

        saveOrFail(user);
        return user.toResponse();
    }

    public void delete(UUID id) {
        User user = findOrFailById(id);
        repository.delete(user);
    }

    private void saveOrFail(User user) {
        try {
            repository.save(user);
        } catch (Exception e) {
            throw new DatabaseException("Error while saving user");
        }
    }
}
