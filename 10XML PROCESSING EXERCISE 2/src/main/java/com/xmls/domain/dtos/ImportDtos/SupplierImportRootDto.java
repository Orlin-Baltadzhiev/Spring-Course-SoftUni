package com.xmls.domain.dtos.ImportDtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "suppliers")
@XmlAccessorType(XmlAccessType.FIELD)
public class SupplierImportRootDto {

    @XmlElement(name = "supplier")
    private List<SupplierImportDto> supplier;


    public SupplierImportRootDto() {
    }

    public List<SupplierImportDto> getSupplier() {
        return supplier;
    }

    public void setSupplier(List<SupplierImportDto> supplier) {
        this.supplier = supplier;
    }
}
