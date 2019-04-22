package ru.kazimir.bortnik.servicedata.model;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ItemDTO {
    @NotNull
    private Long id;
    @NotNull
    @Size(min = 1, max = 40)
    private String name;
    @NotNull
    @Size(min = 1, max = 40)
    private String description;
    private String uniqueNumber;
    @NotNull
    private String status;

    public ItemDTO(String name, String description, String uniqueNumber, String status, Long id) {
        this.name = name;
        this.description = description;
        this.uniqueNumber = uniqueNumber;
        this.status = status;
        this.id = id;
    }

    public ItemDTO() {

    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUniqueNumber() {
        return uniqueNumber;
    }

    public void setUniqueNumber(String uniqueNumber) {
        this.uniqueNumber = uniqueNumber;
    }
}
