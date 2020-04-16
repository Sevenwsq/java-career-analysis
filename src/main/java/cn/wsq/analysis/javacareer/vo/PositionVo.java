package cn.wsq.analysis.javacareer.vo;

import cn.wsq.analysis.javacareer.pojo.Position;
import lombok.*;

/**
 * @author Silent
 * @date 2020/4/13 19:30:53
 * @description
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class PositionVo extends BaseVo<Position> {
    private String jobTitle;
    private String salary;
    private String city;
    private String district;
    private String company;
}
