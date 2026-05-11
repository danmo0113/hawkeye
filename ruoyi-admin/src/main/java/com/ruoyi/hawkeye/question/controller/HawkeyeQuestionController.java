package com.ruoyi.hawkeye.question.controller;

import java.util.List;

import com.ruoyi.hawkeye.question.domain.HawkeyeExportQuestion;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.math3.stat.descriptive.summary.Product;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.hawkeye.question.domain.HawkeyeQuestion;
import com.ruoyi.hawkeye.question.service.IHawkeyeQuestionService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 题库题目Controller
 * 
 * @author 墨
 * @date 2026-04-27
 */
@RestController
@RequestMapping("/hawkeye/question")
public class HawkeyeQuestionController extends BaseController
{
    @Autowired
    private IHawkeyeQuestionService hawkeyeQuestionService;

    /**
     * 查询题库题目列表
     */
    @PreAuthorize("@ss.hasPermi('hawkeye:question:list')")
    @GetMapping("/list")
    public TableDataInfo list(HawkeyeQuestion hawkeyeQuestion)
    {
        startPage();
        List<HawkeyeQuestion> list = hawkeyeQuestionService.selectHawkeyeQuestionList(hawkeyeQuestion);
        return getDataTable(list);
    }

    /**
     * 导出题库题目列表
     */
    @PreAuthorize("@ss.hasPermi('hawkeye:question:export')")
    @Log(title = "题库题目", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HawkeyeQuestion hawkeyeQuestion)
    {
        List<HawkeyeQuestion> list = hawkeyeQuestionService.selectHawkeyeQuestionPathList(hawkeyeQuestion);
        hawkeyeQuestionService.selectHawkeyeQuestionListExport(response,list);
    }

    /**
     * 导入功能
     * @param file
     * @param updateSupport
     * @return
     * @throws Exception
     */
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        // 1. 创建Excel工具类实例，传入待映射的实体类Class对象
        ExcelUtil<HawkeyeExportQuestion> util = new ExcelUtil<>(HawkeyeExportQuestion.class);
        // 2. 调用importExcel方法，将上传的文件流解析成实体对象列表
        List<HawkeyeExportQuestion> productList = util.importExcel(file.getInputStream());
        // 3. 获取当前操作的用户名
        String operName = getUsername();
        // 4. 调用Service层的业务方法处理数据，并返回处理消息
        String message = hawkeyeQuestionService.importData(productList, updateSupport, operName);
        return success(message);
    }

    /**
     * 获取题库题目详细信息
     */
    @PreAuthorize("@ss.hasPermi('hawkeye:question:query')")
    @GetMapping(value = "/{questionId}")
    public AjaxResult getInfo(@PathVariable("questionId") Long questionId)
    {
        return success(hawkeyeQuestionService.selectHawkeyeQuestionByQuestionId(questionId));
    }

    /**
     * 新增题库题目
     */
    @PreAuthorize("@ss.hasPermi('hawkeye:question:add')")
    @Log(title = "题库题目", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HawkeyeQuestion hawkeyeQuestion)
    {
        return toAjax(hawkeyeQuestionService.insertHawkeyeQuestion(hawkeyeQuestion));
    }

    /**
     * 修改题库题目
     */
    @PreAuthorize("@ss.hasPermi('hawkeye:question:edit')")
    @Log(title = "题库题目", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HawkeyeQuestion hawkeyeQuestion)
    {
        return toAjax(hawkeyeQuestionService.updateHawkeyeQuestion(hawkeyeQuestion));
    }

    /**
     * 删除题库题目
     */
    @PreAuthorize("@ss.hasPermi('hawkeye:question:remove')")
    @Log(title = "题库题目", businessType = BusinessType.DELETE)
	@DeleteMapping("/{questionIds}")
    public AjaxResult remove(@PathVariable Long[] questionIds)
    {
        return toAjax(hawkeyeQuestionService.deleteHawkeyeQuestionByQuestionIds(questionIds));
    }
}
