package cn.wsq.analysis.javacareer;

import cn.wsq.analysis.javacareer.controller.MessageController;
import cn.wsq.analysis.javacareer.pojo.Position;
import cn.wsq.analysis.javacareer.service.PositionService;
import cn.wsq.analysis.javacareer.utils.CrawUtils;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest
class JavaCareerAnalysisApplicationTests {

    @Resource
    private CrawUtils crawLagou;
    @Resource
    private PositionService positionService;

    /**
     * 2020年4月13日 13:18:56
     */
    @Test
    void testLagou() throws IOException {
            List<Position> positions = crawLagou.crawLagou(30);
            positionService.saveBatch(positions);
            System.out.println(positions.size());
    }

    @Test
    void testGetGetSalaryByCity() {
        List<String> list = positionService.getSalaryByCity("上海");
        AtomicInteger min = new AtomicInteger(0);
        AtomicInteger max = new AtomicInteger(0);
        AtomicInteger count = new AtomicInteger(0);
        AtomicInteger total = new AtomicInteger(0);
        List<Integer> data = new LinkedList<>();
        min.set(Integer.parseInt(list.get(0).substring(0,list.get(0).indexOf("k"))));
        list.forEach(l->{
            int insideMin = Integer.parseInt(l.substring(0,l.indexOf("k")));
            int insideMax = Integer.parseInt(l.substring(l.indexOf("-")+1, l.length()-1));
            if (insideMin < min.get()) {
                min.set(insideMin);
            }
            if (insideMax > max.get()) {
                max.set(insideMax);
            }
            total.getAndAdd(insideMax+insideMin);
            count.getAndAdd(2);
        });
        data.add(min.get());
        data.add(max.get());
        data.add(total.get()/count.get());
        System.out.println(data);
    }

    @Test
    public void simpleWrite() {
        List<Position> positions = positionService.getBaseMapper().selectList(null);
        // 写法1
        String fileName = "E:\\serve_resources\\" + "simpleWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        EasyExcel.write(fileName, Position.class).sheet("数据").doWrite(positions);

//        // 写法2
//        fileName = "E:\\serve_resources\\" + "simpleWrite" + System.currentTimeMillis() + ".xlsx";
//        // 这里 需要指定写用哪个class去写
//        ExcelWriter excelWriter = EasyExcel.write(fileName, DemoData.class).build();
//        WriteSheet writeSheet = EasyExcel.writerSheet("模板").build();
//        excelWriter.write(data(), writeSheet);
        // 千万别忘记finish 会帮忙关闭流
//        excelWriter.finish();
    }
    @Autowired
    MessageController messageController;
    @Test
    void testSend() throws IOException {
    }

}
