package cn.wsq.analysis.javacareer.service;

import cn.wsq.analysis.javacareer.pojo.Position;
import cn.wsq.analysis.javacareer.vo.PositionVo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Silent
 * @date 2020/4/13 11:39:54
 * @description
 */
public interface PositionService extends IService<Position> {
    /**
     * 分页查询对象
     * @param positionVo
     * @return 分页查询对象，包含分页基础数据和查询结果数据
     */
    PositionVo getAllByPage(PositionVo positionVo);

    /**
     * 根据城市，查询城市的行政区
     * @param city
     * @return
     */
    List<String> getDistrictByCity(String city);

    /**
     * 查询所有城市
     * @return
     */
    List<String> getCity();

    /**
     * 查询所有薪水范围
     * @return
     */
    List<String> getSalary();

    /**
     * 查询职称
     */
    List<String> getJobTitle();

    Integer getCountByJonTitle(String jobTitle);

    List<String> getSalaryByCity(String city);
}
