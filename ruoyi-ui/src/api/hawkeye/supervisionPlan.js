import request from '@/utils/request'

// 查询督学计划列表
export function listSupervisionPlan(query) {
  return request({
    url: '/hawkeye/supervisionPlan/list',
    method: 'get',
    params: query
  })
}

// 查询督学计划详细
export function getSupervisionPlan(planId) {
  return request({
    url: '/hawkeye/supervisionPlan/' + planId,
    method: 'get'
  })
}

// 新增督学计划
export function addSupervisionPlan(data) {
  return request({
    url: '/hawkeye/supervisionPlan',
    method: 'post',
    data: data
  })
}

// 修改督学计划
export function updateSupervisionPlan(data) {
  return request({
    url: '/hawkeye/supervisionPlan',
    method: 'put',
    data: data
  })
}

// 删除督学计划
export function delSupervisionPlan(planId) {
  return request({
    url: '/hawkeye/supervisionPlan/' + planId,
    method: 'delete'
  })
}

// 复制督学计划
export function copySupervisionPlan(planId) {
  return request({
    url: '/hawkeye/supervisionPlan/copy/' + planId,
    method: 'post'
  })
}

// 变更督学计划状态
export function changeSupervisionPlanStatus(planId, status) {
  return request({
    url: '/hawkeye/supervisionPlan/changeStatus',
    method: 'put',
    data: { planId, status }
  })
}