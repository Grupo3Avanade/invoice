package com.avanade.invoice.entities;

import com.avanade.invoice.payloads.request.RequestUser;
import com.avanade.invoice.payloads.response.ResponseUser;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID sharedId;

    @Column(nullable = false)
    private String name;

    @CreationTimestamp
    @Column(nullable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private Date updatedAt;

    public User(RequestUser request) {
        this.sharedId = request.sharedId();
        this.name = request.name();
    }

    public ResponseUser toResponse() {
        return new ResponseUser(
                id,
                sharedId,
                name,
                createdAt,
                updatedAt
        );
    }
}
