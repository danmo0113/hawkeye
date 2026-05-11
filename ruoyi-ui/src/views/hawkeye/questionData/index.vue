<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
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
          v-hasPermi="['hawkeye:questionData:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['hawkeye:questionData:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['hawkeye:questionData:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['hawkeye:questionData:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="questionDataList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="所属分类" align="center" prop="categoryName" />
      <el-table-column label="材料内容" align="center">
        <template slot-scope="scope">
          <div v-html="scope.row.qdContent"></div>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['hawkeye:questionData:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['hawkeye:questionData:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改题目材料对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="70%" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="所属分类" prop="categoryId">
              <el-cascader :props="{ emitPath: false }" v-model="form.categoryId" :options="categoryOptions"></el-cascader>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="材料内容">
              <editor v-model="form.qdContent" :min-height="192"/>
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
import { listQuestionData, getQuestionData, delQuestionData, addQuestionData, updateQuestionData } from "@/api/hawkeye/questionData"
import { listCategory } from "@/api/hawkeye/category"

export default {
  name: "QuestionData",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 题目材料表格数据
      questionDataList: [],
      //所属分类树选项
      categoryOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        categoryId: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        qdContent: [
          { required: true, message: "材料内容不能为空", trigger: "blur" }
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询题目材料列表 */
    getList() {
      this.loading = true
      listQuestionData(this.queryParams).then(response => {
        this.questionDataList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    /** 查询分类管理下拉树结构 */
    getCategoryTreeselect() {
      listCategory().then(response => {
        this.categoryOptions = this.convertTree(this.handleTree(response.data, "categoryId", "parentId"));
      })
    },
    convertTree(originalArray) {
      if (!Array.isArray(originalArray)) return [];
      return originalArray.map(item => {
        // 提取需要转换的字段，其余字段保留
        const { categoryId, categoryName, children, ...rest } = item;
        
        const newNode = {
          value: categoryId,
          label: categoryName,
          ...rest
        };
        
        if (children && children.length) {
          newNode.children = this.convertTree(children);
        }
        
        return newNode;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        qdId: null,
        categoryId: null,
        qdContent: null
      }
      this.resetForm("form")
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm")
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.qdId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.getCategoryTreeselect()
      this.open = true
      this.title = "添加题目材料"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      this.getCategoryTreeselect()
      const qdId = row.qdId || this.ids
      getQuestionData(qdId).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改题目材料"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.qdId != null) {
            updateQuestionData(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addQuestionData(this.form).then(response => {
              this.$modal.msgSuccess("新增成功")
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const qdIds = row.qdId || this.ids
      this.$modal.confirm('是否确认删除题目材料编号为"' + qdIds + '"的数据项？').then(function() {
        return delQuestionData(qdIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('hawkeye/questionData/export', {
        ...this.queryParams
      }, `questionData_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
