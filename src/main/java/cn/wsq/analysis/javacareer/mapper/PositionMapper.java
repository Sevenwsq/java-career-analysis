package cn.wsq.analysis.javacareer.mapper;

import cn.wsq.analysis.javacareer.pojo.Position;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Silent
 * @date 2020/4/13 11:36:43
 * @description
 */
public interface PositionMapper extends BaseMapper<Position> {
    List<String> getCity();
    List<String> getDistrictByCity(@Param("city") String city);
    List<String> getSalary();
    List<String> getJobTitle();
    Integer getCountByJonTitle(@Param("jobTitle") String jobTitle);

    List<String> getSalaryByCity(@Param("city") String city);
}
