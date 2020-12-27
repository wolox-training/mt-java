package com.wolox.training.dtos;

import lombok.Data;

@Data
public class PublisherDTO {

    public String name;

    @Override
    public String toString(){
        return this.name;
    }
}
