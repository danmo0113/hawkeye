<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="分类名称" prop="categoryName">
        <el-input
          v-model="queryParams.categoryName"
          placeholder="请输入分类名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
	    <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['hawkeye:category:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-check"
          size="mini"
          @click="handleSaveSort"
          v-hasPermi="['hawkeye:category:edit']"
        >保存排序</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-sort"
          size="mini"
          @click="toggleExpandAll"
        >展开/折叠</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table
      v-if="refreshTable"
      v-loading="loading"
      :data="categoryList"
      row-key="categoryId"
      :default-expand-all="isExpandAll"
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
    >
      <el-table-column label="分类名称" align="center" prop="categoryName" />
      <el-table-column prop="orderNum" label="排序" width="200">
        <template slot-scope="scope">
          <el-input-number v-model="scope.row.sortOrder" controls-position="right" :min="0" size="mini" style="width: 88px" />
        </template>
      </el-table-column>
      <!-- <el-table-column label="层级" align="center" prop="level" /> -->
      <el-table-column prop="status" label="状态" width="80">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.category_status" :value="scope.row.status" />
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['hawkeye:category:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-plus"
            @click="handleAdd(scope.row)"
            v-hasPermi="['hawkeye:category:add']"
          >新增</el-button>
          <el-button
            v-if="scope.row.status == 0"
            size="mini"
            type="text"
            icon="el-icon-plus"
            @click="handleDeactivate(scope.row)"
          >停用</el-button>
          <el-button
            v-if="scope.row.status == 1"
            size="mini"
            type="text"
            icon="el-icon-plus"
            @click="handleEnable(scope.row)"
          >启用</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['hawkeye:category:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加或修改考试分类对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="分类名称" prop="categoryName">
              <el-input v-model="form.categoryName" placeholder="请输入分类名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="显示顺序" prop="sortOrder">
              <el-input v-model="form.sortOrder" placeholder="请输入显示顺序" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listCategory, getCategory, delCategory, addCategory, updateCategory, updateCategorySort } from "@/api/hawkeye/category"
import Treeselect from "@riophae/vue-treeselect"
import "@riophae/vue-treeselect/dist/vue-treeselect.css"

export default {
  name: "Category",
  dicts: ['category_status'],
  components: {
    Treeselect
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 考试分类表格数据
      categoryList: [],
      // 考试分类树选项
      categoryOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否展开，默认全部展开
      isExpandAll: true,
      // 重新渲染表格状态
      refreshTable: true,
      // 记录原始排序，用于对比变更
      originalOrders: {},
      // 查询参数
      queryParams: {
        categoryName: null,
        sortOrder: null,
        icon: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        categoryName: [
          { required: true, message: "分类名称不能为空", trigger: "blur" }
        ],
        // level: [
        //   { required: true, message: "层级不能为空", trigger: "blur" }
        // ],
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询考试分类列表 */
    getList() {
      this.loading = true
      listCategory(this.queryParams).then(response => {
        this.categoryList = this.handleTree(response.data, "categoryId", "parentId")
        this.recordOriginalOrders(this.categoryList)
        this.loading = false
      })
    },
    /** 转换考试分类数据结构 */
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children
      }
      return {
        id: node.categoryId,
        label: node.categoryName,
        children: node.children
      }
    },
	/** 查询考试分类下拉树结构 */
    getTreeselect() {
      listCategory().then(response => {
        this.categoryOptions = []
        const data = { categoryId: 0, categoryName: '顶级节点', children: [] }
        data.children = this.handleTree(response.data, "categoryId", "parentId")
        this.categoryOptions.push(data)
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        categoryId: null,
        parentId: null,
        categoryName: null,
        level: null,
        sortOrder: null,
        icon: null,
        delFlag: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      }
      this.resetForm("form")
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm")
      this.handleQuery()
    },
    /** 新增按钮操作 */
    handleAdd(row) {
      this.reset()
      this.getTreeselect()
      if (row != null && row.categoryId) {
        this.form.parentId = row.categoryId
      } else {
        this.form.parentId = 0
      }
      this.open = true
      this.title = "添加考试分类"
    },
    /** 展开/折叠操作 */
    toggleExpandAll() {
      this.refreshTable = false
      this.isExpandAll = !this.isExpandAll
      this.$nextTick(() => {
        this.refreshTable = true
      })
    },
    /** 递归记录原始排序 */
    recordOriginalOrders(list) {
      list.forEach(item => {
        this.originalOrders[item.categoryId] = item.sortOrder
        if (item.children && item.children.length) {
          this.recordOriginalOrders(item.children)
        }
      })
    },
    /** 保存排序 */
    handleSaveSort() {
      const changedCategoryIds = []
      const changedOrderNums = []
      const collectChanged = (list) => {
        list.forEach(item => {
          if (String(this.originalOrders[item.categoryId]) !== String(item.sortOrder)) {
            changedCategoryIds.push(item.categoryId)
            changedOrderNums.push(item.sortOrder)
          }
          if (item.children && item.children.length) {
            collectChanged(item.children)
          }
        })
      }
      collectChanged(this.categoryList)
      if (changedCategoryIds.length === 0) {
        this.$modal.msgWarning("未检测到排序修改")
        return
      }
      updateCategorySort({ categoryIds: changedCategoryIds.join(","), orderNums: changedOrderNums.join(",") }).then(() => {
        this.$modal.msgSuccess("排序保存成功")
        this.recordOriginalOrders(this.categoryList)
      })
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      this.getTreeselect()
      if (row != null) {
        this.form.parentId = row.parentId
      }
      getCategory(row.categoryId).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改考试分类"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.categoryId != null) {
            updateCategory(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addCategory(this.form).then(response => {
              this.$modal.msgSuccess("新增成功")
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    //停用按钮操作
    handleDeactivate(row) {
      this.$modal.confirm('是否停用分类为"' + row.categoryName + '"的数据项？').then(function() {
        const categoryForm = {
          categoryId: row.categoryId,
          status: 1,
        }
        return updateCategory(categoryForm)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("停用成功")
      }).catch(() => {})
    },
    //启用按钮操作
    handleEnable(row) {
      this.$modal.confirm('是否启用分类为"' + row.categoryName + '"的数据项？').then(function() {
        const categoryForm = {
          categoryId: row.categoryId,
          status: 0,
        }
        return updateCategory(categoryForm)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("启用成功")
      }).catch(() => {})
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      this.$modal.confirm('是否确认删除分类为"' + row.categoryName + '"的数据项？').then(function() {
        return delCategory(row.categoryId)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    }
  }
}
</script>
