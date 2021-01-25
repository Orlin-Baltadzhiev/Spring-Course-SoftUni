package softuni.cardealer.domain.dtos.export;

import com.google.gson.annotations.Expose;

public class SalesExportDto {
//    @Expose
//    private long id;
    @Expose
    private String name;

    public SalesExportDto() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
