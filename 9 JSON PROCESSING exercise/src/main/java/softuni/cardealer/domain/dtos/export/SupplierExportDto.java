package softuni.cardealer.domain.dtos.export;

import com.google.gson.annotations.Expose;
import softuni.cardealer.domain.entities.Part;

public class SupplierExportDto {
    @Expose
    private long id;
    @Expose
    private String name;
    @Expose
    private int partCount;

    public SupplierExportDto() {
    }

    public int getPartCount() {
        return partCount;
    }

    public void setPartCount(int partCount) {
        this.partCount = partCount;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
