package softuni.cardealer.domain.dtos.export;

import com.google.gson.annotations.Expose;


import java.time.LocalDateTime;
import java.util.Set;

public class CustomerExportDto {

    @Expose
    private long id;

    @Expose
    private String name;

    @Expose
    private LocalDateTime birthDate;

    @Expose
    private boolean isYoungDriver;

    @Expose
    private Set<SalesExportDto> sales;



    public CustomerExportDto() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<SalesExportDto> getSales() {
        return sales;
    }

    public void setSales(Set<SalesExportDto> sales) {
        this.sales = sales;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDateTime birthDate) {
        this.birthDate = birthDate;
    }

    public boolean isYoungDriver() {
        return isYoungDriver;
    }

    public void setYoungDriver(boolean youngDriver) {
        isYoungDriver = youngDriver;
    }


    public Set<SalesExportDto> getSalesExportDtos() {
        return sales;
    }

    public void setSalesExportDto(Set<SalesExportDto> sales) {
        this.sales = sales;
    }

}
