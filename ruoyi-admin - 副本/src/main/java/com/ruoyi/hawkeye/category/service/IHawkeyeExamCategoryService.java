package com.ruoyi.hawkeye.category.service;

import java.util.List;
import com.ruoyi.hawkeye.category.domain.HawkeyeExamCategory;

/**
 * 考试分类Service接口
 * 
 * @author 墨
 * @date 2026-04-20
 */
public interface IHawkeyeExamCategoryService 
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
     * 保存菜单排序
     *
     * @param categoryIds 菜单ID
     * @param orderNums 排序ID
     */
    public void updateHawkeyeExamCategorySort(String[] categoryIds, String[] orderNums);

    /**
     * 批量删除考试分类
     * 
     * @param categoryIds 需要删除的考试分类主键集合
     * @return 结果
     */
    public int deleteHawkeyeExamCategoryByCategoryIds(Long[] categoryIds);

    /**
     * 删除考试分类信息
     * 
     * @param categoryId 考试分类主键
     * @return 结果
     */
    public int deleteHawkeyeExamCategoryByCategoryId(Long categoryId);
}
