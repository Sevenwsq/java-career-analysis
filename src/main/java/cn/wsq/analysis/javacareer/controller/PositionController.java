package cn.wsq.analysis.javacareer.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.wsq.analysis.javacareer.common.ResultObj;
import cn.wsq.analysis.javacareer.pojo.Position;
import cn.wsq.analysis.javacareer.service.PositionService;
import cn.wsq.analysis.javacareer.vo.PositionVo;
import com.alibaba.excel.EasyExcel;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Silent
 * @date 2020/4/13 11:42:00
 * @description
 */
@RestController
@RequestMapping("/position")
public class PositionController {
    private final PositionService positionService;

    public PositionController(PositionService positionService) {
        this.positionService = positionService;
    }

    @PostMapping("/page")
    public ResultObj<PositionVo> getAllByPage(@RequestBody PositionVo positionVo) {
        try {
            PositionVo positionPage = this.positionService.getAllByPage(positionVo);
            return new ResultObj<>("查询成功", positionPage);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultObj<>(400, "查询失败");
        }
    }

    @GetMapping("/cities")
    public List<String> getAllCity() {
        return this.positionService.getCity();
    }

    @GetMapping("/salaries")
    public List<String> getSalary() {
        return this.positionService.getSalary();
    }


    @GetMapping("/job_title")
    public List<Map<String, String>> getJobTitle(String jobTitle) {
        List<String> list = this.positionService.getJobTitle();
        List<Map<String, String>> result = new LinkedList<>();
        list.forEach(l -> {
            Map<String, String> map = new HashMap<>();
            map.put("value", l);
            result.add(map);
        });
        return result;
    }

    @GetMapping("/district/{city}")
    public List<String> getDistrictByCity(@PathVariable String city) {
        System.out.println(city);
        List<String> districtByCity = this.positionService.getDistrictByCity(city);
        districtByCity.removeIf(StrUtil::isBlank);
        return districtByCity;
    }

    @GetMapping("/histogram")
    public Map<String, List<String>> getHistogram() {
        Map<String, List<String>> map = new HashMap<>();
        List<String> jobTitleList = this.positionService.getJobTitle();
        List<String> countList = new LinkedList<>();
        jobTitleList.forEach(jobTitle -> {
            Integer count = this.positionService.getCountByJonTitle(jobTitle);
            countList.add(count.toString());
        });
        map.put("jobTitles", jobTitleList);
        map.put("counts", countList);
        return map;
    }

    @GetMapping("/analysis/salary")
    public List<List<Integer>> analysisSalary() {
        List<List<Integer>> list = new LinkedList<>();
        List<String> cityList = this.positionService.getCity();
        cityList.forEach(city -> {
            List<Integer> data = this.getSalaryAnalysisData(city);
            list.add(data);
        });
        return list;
    }

    /**
     * 文件下载并且失败的时候返回json（默认失败了会返回一个有部分数据的Excel）
     *
     * @since 2.1.1
     */
    @GetMapping("/excel")
    public void downloadAllForExcel(HttpServletResponse response) throws IOException {
        List<Position> positions = positionService.getBaseMapper().selectList(null);
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            // SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String fileName = URLEncoder.encode("招聘信息" + System.currentTimeMillis(), "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            // 这里需要设置不关闭流
            EasyExcel.write(response.getOutputStream(), Position.class).autoCloseStream(Boolean.FALSE).sheet("模板")
                    .doWrite(positions);
        } catch (Exception e) {
            // 重置response
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            Map<String, String> map = new HashMap<>();
            map.put("status", "failure");
            map.put("message", "下载文件失败" + e.getMessage());
            response.getWriter().println(JSONUtil.parseFromMap(map));
        }
    }

    @PostMapping("/excel/page")
    public void downloadPageForExcel(HttpServletResponse response, @RequestBody Map<String, List<Position>> reqMap) throws IOException {
        List<Position> data = reqMap.get("data");
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("招聘信息" + System.currentTimeMillis(), "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            EasyExcel.write(response.getOutputStream(), Position.class).autoCloseStream(Boolean.FALSE).sheet("模板")
                    .doWrite(data);
        } catch (Exception e) {
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            Map<String, String> map = new HashMap<>();
            map.put("status", "failure");
            map.put("message", "下载文件失败" + e.getMessage());
            response.getWriter().println(JSONUtil.parseFromMap(map));
        }
    }

    /**
     * 获取一个城市薪资的最小值，最大值，平均值
     */
    private List<Integer> getSalaryAnalysisData(String city) {
        List<String> list = this.positionService.getSalaryByCity(city);
        AtomicInteger min = new AtomicInteger(0);
        AtomicInteger max = new AtomicInteger(0);
        AtomicInteger count = new AtomicInteger(0);
        AtomicInteger total = new AtomicInteger(0);
        List<Integer> data = new LinkedList<>();
        min.set(Integer.parseInt(list.get(0).substring(0, list.get(0).indexOf("k"))));
        list.forEach(l -> {
            int insideMin = Integer.parseInt(l.substring(0, l.indexOf("k")));
            int insideMax = Integer.parseInt(l.substring(l.indexOf("-") + 1, l.length() - 1));
            if (insideMin < min.get()) {
                min.set(insideMin);
            }
            if (insideMax > max.get()) {
                max.set(insideMax);
            }
            total.getAndAdd(insideMax + insideMin);
            count.getAndAdd(2);
        });
        data.add(min.get());
        data.add(max.get());
        data.add(total.get() / count.get());
        return data;
    }

}
