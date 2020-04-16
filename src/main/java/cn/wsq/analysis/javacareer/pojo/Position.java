package cn.wsq.analysis.javacareer.pojo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author Silent
 * @date 2020/4/10 11:42:31
 * @description
 */
@Data
public class Position {
    @TableId(type = IdType.AUTO)
    @ExcelProperty("编号")
    private Integer id;

    @ExcelProperty("职位名称")
    private String jobTitle;

    @ExcelProperty("薪水")
    private String salary;

    @ExcelProperty("城市")
    private String city;

    @ExcelProperty("行政区")
    private String district;

    @ExcelProperty("公司名称")
    private String company;

    @ExcelIgnore
    private String companyLink;
    @ExcelIgnore
    private String companyLogo;

    @ExcelProperty("工作经验")
    private String exRequired;

    @ExcelProperty("学历")
    private String education;

    @ExcelProperty("标签")
    private String tags;

}
