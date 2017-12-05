package com.example.app.whiteboardcoop.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "WHITEBOARD")
public class Whiteboard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WHITEBOARD_ID")
    private long id;

    private byte[] image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User owner;

    @ManyToMany
    @JoinTable(name = "whiteboard_readers",
        joinColumns = @JoinColumn(name = "WHITEBOARD_ID"),
        inverseJoinColumns = @JoinColumn(name = "USER_ID"))
    private Set<User> readers;

    @ManyToMany
    @JoinTable(name = "whiteboard_writers",
        joinColumns = @JoinColumn(name = "WHITEBOARD_ID"),
        inverseJoinColumns = @JoinColumn(name = "USER_ID"))
    private Set<User> writers;

    private boolean active;

    private LocalDateTime creationDateTime;

    private String password;
    private boolean publicRead;
    private boolean publicWrite;
    private boolean privateRead;
    private boolean privateWrite;

    public Whiteboard() {
    }

    public Whiteboard(User owner) {
        this.owner = owner;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Set<User> getReaders() {
        return readers;
    }

    public void setReaders(Set<User> readers) {
        this.readers = readers;
    }

    public Set<User> getWriters() {
        return writers;
    }

    public void setWriters(Set<User> writers) {
        this.writers = writers;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isPublicRead() {
        return publicRead;
    }

    public void setPublicRead(boolean publicRead) {
        this.publicRead = publicRead;
    }

    public boolean isPublicWrite() {
        return publicWrite;
    }

    public void setPublicWrite(boolean publicWrite) {
        this.publicWrite = publicWrite;
    }

    public boolean isPrivateRead() {
        return privateRead;
    }

    public void setPrivateRead(boolean privateRead) {
        this.privateRead = privateRead;
    }

    public boolean isPrivateWrite() {
        return privateWrite;
    }

    public void setPrivateWrite(boolean privateWrite) {
        this.privateWrite = privateWrite;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Whiteboard that = (Whiteboard) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .toHashCode();
    }
}
