package ru.iav.takoe.countee.da.impl;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import ru.iav.takoe.countee.vo.Cost;

class CostsData implements Serializable {

    private Map<String, Cost> descriptor;

    CostsData() {
        descriptor = new LinkedHashMap<>();
    }

    Map<String, Cost> getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(Map<String, Cost> descriptor) {
        this.descriptor = descriptor;
    }
}
