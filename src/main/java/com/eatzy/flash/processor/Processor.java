package com.eatzy.flash.processor;

public interface Processor<I,R> {
    R process(I i);
}
