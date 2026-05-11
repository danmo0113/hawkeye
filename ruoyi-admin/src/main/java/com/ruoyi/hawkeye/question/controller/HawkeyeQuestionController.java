package com.ruoyi.hawkeye.question.controller;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
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
//        List<HawkeyeQuestion> list = hawkeyeQuestionService.selectHawkeyeQuestionListExport(response,hawkeyeQuestion);
//        ExcelUtil<HawkeyeQuestion> util = new ExcelUtil<HawkeyeQuestion>(HawkeyeQuestion.class);
//        util.exportExcel(response, list, "题库题目数据");
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
