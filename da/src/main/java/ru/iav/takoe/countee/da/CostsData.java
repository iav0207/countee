package ru.iav.takoe.countee.da;

import ru.iav.takoe.countee.vo.Cost;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by takoe on 16.08.16.
 */
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
