package cn.wsq.analysis.javacareer.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.wsq.analysis.javacareer.mapper.PositionMapper;
import cn.wsq.analysis.javacareer.pojo.Position;
import cn.wsq.analysis.javacareer.service.PositionService;
import cn.wsq.analysis.javacareer.vo.PositionVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Silent
 * @date 2020/4/13 11:38:54
 * @description
 */
@Service
public class PositionServiceImpl extends ServiceImpl<PositionMapper, Position> implements PositionService {
    @Override
    public PositionVo getAllByPage(PositionVo positionVo) {
        System.out.println(positionVo);
        IPage<Position> page = new Page<>(positionVo.getCurrent(), positionVo.getSize());
        QueryWrapper<Position> wrapper = new QueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(positionVo.getJobTitle()),"job_title", positionVo.getJobTitle());
        wrapper.like(StrUtil.isNotBlank(positionVo.getCompany()),"company", positionVo.getCompany());
        wrapper.eq(StrUtil.isNotBlank(positionVo.getCity()),"city", positionVo.getCity());
        wrapper.eq(StrUtil.isNotBlank(positionVo.getDistrict()),"district", positionVo.getDistrict());
        wrapper.eq(StrUtil.isNotBlank(positionVo.getSalary()),"salary", positionVo.getSalary());
        if (StrUtil.isNotBlank(positionVo.getSortColumn()) && StrUtil.isNotBlank(positionVo.getSortMethod())){
            if (positionVo.getSortMethod().contains("asc") || positionVo.getSortMethod().contains("ASC")) {
                wrapper.orderByAsc(positionVo.getSortColumn());
            } else {
                wrapper.orderByDesc(positionVo.getSortColumn());
            }
        }
        this.baseMapper.selectPage(page, wrapper);
        positionVo.setPages(page.getPages());
        positionVo.setTotal(page.getTotal());
        positionVo.setList(page.getRecords());
        return positionVo;
    }

    @Override
    public List<String> getDistrictByCity(String city) {
        return this.baseMapper.getDistrictByCity(city);
    }

    @Override
    public List<String> getCity() {
        return this.baseMapper.getCity();
    }

    @Override
    public List<String> getSalary() {
        return this.baseMapper.getSalary();
    }

    @Override
    public List<String> getJobTitle() {
        return this.baseMapper.getJobTitle();
    }

    @Override
    public Integer getCountByJonTitle(String jobTitle) {
        return this.baseMapper.getCountByJonTitle(jobTitle);
    }

    @Override
    public List<String> getSalaryByCity(String city) {
        return this.baseMapper.getSalaryByCity(city);
    }
}
