package com.wolox.training.dtos;

import lombok.Data;

@Data
public class AuthorDTO {

    public String name;

    @Override
    public String toString(){
        return this.name;
    }

}
