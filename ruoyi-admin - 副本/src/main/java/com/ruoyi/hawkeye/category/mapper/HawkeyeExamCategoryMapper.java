package com.ruoyi.hawkeye.category.mapper;

import java.util.List;
import com.ruoyi.hawkeye.category.domain.HawkeyeExamCategory;

/**
 * 考试分类Mapper接口
 * 
 * @author 墨
 * @date 2026-04-20
 */
public interface HawkeyeExamCategoryMapper 
{
    /**
     * 查询考试分类
     * 
     * @param categoryId 考试分类主键
     * @return 考试分类
     */
    public HawkeyeExamCategory selectHawkeyeExamCategoryByCategoryId(Long categoryId);

    /**
     * 查询考试分类列表
     * 
     * @param hawkeyeExamCategory 考试分类
     * @return 考试分类集合
     */
    public List<HawkeyeExamCategory> selectHawkeyeExamCategoryList(HawkeyeExamCategory hawkeyeExamCategory);

    /**
     * 新增考试分类
     * 
     * @param hawkeyeExamCategory 考试分类
     * @return 结果
     */
    public int insertHawkeyeExamCategory(HawkeyeExamCategory hawkeyeExamCategory);

    /**
     * 修改考试分类
     * 
     * @param hawkeyeExamCategory 考试分类
     * @return 结果
     */
    public int updateHawkeyeExamCategory(HawkeyeExamCategory hawkeyeExamCategory);

    /**
     * 删除考试分类
     * 
     * @param categoryId 考试分类主键
     * @return 结果
     */
    public int deleteHawkeyeExamCategoryByCategoryId(Long categoryId);

    /**
     * 批量删除考试分类
     * 
     * @param categoryIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteHawkeyeExamCategoryByCategoryIds(Long[] categoryIds);
}
