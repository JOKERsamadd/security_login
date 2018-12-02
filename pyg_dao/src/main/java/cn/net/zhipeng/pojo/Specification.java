package cn.net.zhipeng.pojo;

import java.io.Serializable;
import java.util.List;

public class Specification implements Serializable {
    private Long id;

    private String specName;

    private List<SpecificationOption> specificationOptionList;

    private static final long serialVersionUID = 1L;

    public List<SpecificationOption> getSpecificationOptionList() {
        return specificationOptionList;
    }

    public void setSpecificationOptionList(List<SpecificationOption> specificationOptionList) {
        this.specificationOptionList = specificationOptionList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName == null ? null : specName.trim();
    }
}