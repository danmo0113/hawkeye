<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="题型" prop="questionType">
        <el-select v-model="queryParams.questionType" placeholder="请选择题型" clearable>
          <el-option
            v-for="dict in dict.type.question_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="所属章节知识点" prop="kpIds" label-width="auto">
        <el-cascader :props="{ emitPath: false,checkStrictly: true, multiple:true }" 
        v-model="queryParams.kpIds" :options="knowledgePointOptions"
        collapse-tags clearable filterable></el-cascader>
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
          v-hasPermi="['hawkeye:question:add']"
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
          v-hasPermi="['hawkeye:question:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-upload2"
          size="mini"
          @click="handleImport"
        >导入</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['hawkeye:question:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['hawkeye:question:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="questionList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="题型" align="center" prop="questionType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.question_type" :value="scope.row.questionType"/>
        </template>
      </el-table-column>
      <el-table-column label="所属分类" prop="kpNames" />
      <el-table-column label="题干内容" align="center">
        <template slot-scope="scope">
          <div v-html="scope.row.questionContent"></div>
        </template>
      </el-table-column>
      <el-table-column label="正确答案" align="center">
        <template slot-scope="scope">
          <div v-html="scope.row.correctAnswer"></div>
        </template>
      </el-table-column>
      <el-table-column label="答案解析" align="center">
        <template slot-scope="scope">
          <div v-html="scope.row.analysis"></div>
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
            v-hasPermi="['hawkeye:question:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['hawkeye:question:remove']"
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

    <!-- 添加或修改题库题目对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="80%" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="题型" prop="questionType">
              <el-select v-model="form.questionType" placeholder="请选择题型" @change="changeQuestionType">
                <el-option
                  v-for="dict in dict.type.question_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="所属章节知识点" prop="kpIds" label-width="auto">
              <el-cascader :props="{ emitPath: false,checkStrictly: true, multiple:true }" 
              v-model="form.kpIds" :options="knowledgePointOptions"
              collapse-tags clearable filterable></el-cascader>
            </el-form-item>
          </el-col>
          <!-- 材料题 -->
          <div v-if="form.questionType=='MATERIAL'">
            <el-col :span="24">
              <el-form-item label="相关材料">
                <el-select v-model="form.materialId" placeholder="请选择相关材料" style="width: 100%;">
                  <el-option v-for="item in questionDataList" :key="item.qdId" :label="item.qbContentText" :value="item.qdId"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="材料题题型" prop="questionTypeChildren">
                <el-select v-model="form.questionTypeChildren" placeholder="请选择材料题题型" @change="changeQuestionType">
                  <el-option v-for="dict in dict.type.question_type" v-if="dict.value!='MATERIAL'"
                  :key="dict.value" :label="dict.label" :value="dict.value"></el-option>
                  <el-option v-for="dict in dict.type.question_type" v-if="dict.value=='MATERIAL'" :disabled="true" 
                  :key="dict.value" :label="dict.label" :value="dict.value"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </div>
          
          <el-col :span="24">
            <el-form-item label="题干内容">
              <editor v-model="form.questionContent" :min-height="192"/>
            </el-form-item>
          </el-col>
          <!-- 题目类型是单选、多选、不定项选择-->
          <el-col :span="24" v-if="form.questionType=='SINGLE'||form.questionType=='MULTI'||form.questionType=='MULTIPLE' || 
          form.questionTypeChildren=='SINGLE'||form.questionTypeChildren=='MULTI'||form.questionTypeChildren=='MULTIPLE'">
            <el-row :gutter="20" v-for="(option,index) in form.optionsList">
              <el-col :span="18">
                <el-form-item :label="'选项'+option.label">
                  <editor v-model="option.content" :min-height="100"/>
                </el-form-item>
              </el-col>
              <el-col :span="3">
                <el-button type="danger" plain icon="el-icon-delete-solid" @click="delectOption(index)">删除选项</el-button>
              </el-col>
              <el-col :span="3">
                <el-checkbox v-model="form.correctAnswerList[index]" 
                v-if="form.questionType=='MULTI'||form.questionType=='MULTIPLE'
                || form.questionTypeChildren=='MULTI'||form.questionTypeChildren=='MULTIPLE'">正确答案</el-checkbox>
                <el-radio v-model="form.correctAnswer" :label="option.label" 
                v-if="form.questionType=='SINGLE' || form.questionTypeChildren=='SINGLE'">正确答案</el-radio>
              </el-col>
            </el-row>
            <el-button type="primary" plain @click="addQuestion">新增选项</el-button>
          </el-col>
          <!-- 填空题创建答案 -->
          <el-col :span="24" v-if="form.questionType=='GAPFILL' || form.questionTypeChildren=='GAPFILL'">
            <el-row :gutter="20" v-for="(answer,index) in form.gapFillAnswerList">
              <el-col :span="21">
                <el-form-item :label="'答案' + answer.label">
                  <el-input v-model="answer['content']" placeholder="请输入答案"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="3">
                <el-button type="danger" plain icon="el-icon-delete-solid" @click="delectAnswer(index)">删除答案</el-button>
              </el-col>
            </el-row>
            <el-button type="primary" plain @click="addAnswer">答案选项</el-button>
          </el-col>
          <!-- 判断题选项 -->
          <el-col :span="24" v-if="form.questionType=='JUDGE' || form.questionTypeChildren=='JUDGE'">
            <el-form-item label="答案">
              <el-radio v-model="form.correctAnswer" :label="'1'">正确</el-radio>
              <el-radio v-model="form.correctAnswer" :label="'0'">错误</el-radio>
            </el-form-item>
          </el-col>
          <!-- 问答题参考答案 -->
          <el-col :span="24" v-if="form.questionType=='ESSAY' || form.questionTypeChildren=='ESSAY'">
            <el-form-item label="参考答案">
              <editor v-model="form.correctAnswer" :min-height="192"/>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="答案解析">
              <editor v-model="form.analysis" :min-height="192"/>
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
    <!-- 用户导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload
        ref="uploadRef"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip" slot="tip">
          <el-checkbox v-model="upload.updateSupport" /> 是否更新已经存在的产品数据
          <span slot="tip" class="el-upload__tip">仅允许导入xls、xlsx格式文件。</span>
          <el-link type="primary" :underline="false" style="font-size:12px;vertical-align: baseline;" @click="importTemplate">下载模板</el-link>
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listQuestion, getQuestion, delQuestion, addQuestion, updateQuestion } from "@/api/hawkeye/question"
import { listKnowledgePoint } from "@/api/hawkeye/knowledgePoint"
import { listQuestionData } from "@/api/hawkeye/questionData"
import Treeselect from "@riophae/vue-treeselect"
import "@riophae/vue-treeselect/dist/vue-treeselect.css"
import TreePanel from "@/components/TreePanel"
import ExcelImportDialog from "@/components/ExcelImportDialog"
import { getToken } from "@/utils/auth";

export default {
  name: "Question",
  dicts: ['question_type'],
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
      // 题库题目表格数据
      questionList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      knowledgePointOptions: [],
      //材料列表
      questionDataList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        categoryId: null,
        questionType: null,
        kpIds:[],
        questionTypeChildren: null,
        questionContent: null,
        correctAnswer: null,
        correctAnswerList: [],
        analysis: null,
        kpIds: [],
        materialContent: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        categoryId: [
          { required: true, message: "所属三级分类ID不能为空", trigger: "change" }
        ],
        questionType: [
          { required: true, message: "题型不能为空", trigger: "change" }
        ],
        questionContent: [
          { required: true, message: "题干内容不能为空", trigger: "blur" }
        ],
        correctAnswer: [
          { required: true, message: "正确答案不能为空", trigger: "change" }
        ],
      },
      // 导入对话框相关数据
      upload: {
        // 是否显示弹出层
        open: false,
        // 弹出层标题
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址（后端接口地址）
        url: process.env.VUE_APP_BASE_API + "/hawkeye/question/importData",
      },
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询题库题目列表 */
    getList() {
      this.loading = true
      this.getKnowledgePointTreeselect();
      listQuestion(this.queryParams).then(response => {
        this.questionList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    //删除选项
    delectOption(index) {
      this.form.optionsList.splice(index, 1);
      for (let i=0; i< this.form.optionsList.length; i++){
        this.form.optionsList[i].label=String.fromCharCode(i + 65);
      }
    },
    //新增选项
    addQuestion(){
      const option={
        label: String.fromCharCode(this.form.optionsList.length + 65),
        content: null,
      };
      this.form.optionsList.push(option)
    },
    //删除填空题答案
    delectAnswer(index) {
      this.form.gapFillAnswerList.splice(index, 1);
      for (let i=0; i< this.form.gapFillAnswerList.length; i++){
        this.form.gapFillAnswerList[i].label = i + 1 + "";
      }
    },
    //新增填空题答案
    addAnswer(){
      const answer={
        label: this.form.gapFillAnswerList.length + 1 + "",
        content: null,
      };
      this.form.gapFillAnswerList.push(answer)
    },
    //修改题目类型
    changeQuestionType(){
      if(this.form.questionType=='MULTI' || this.form.questionType=='MULTIPLE' || this.form.questionType=='SINGLE' ||
      this.form.questionTypeChildren=='MULTI' || this.form.questionTypeChildren=='MULTIPLE' || this.form.questionTypeChildren=='SINGLE') {
        this.form.optionsList=[
          {
            label: "A",
            content: null,
          },
          {
            label: "B",
            content: null,
          },
          {
            label: "C",
            content: null,
          },
          {
            label: "D",
            content: null,
          },
        ];
        this.form.gapFillAnswerList=[];
        this.form.correctAnswerList = [];
        if(this.form.questionType=='MULTI' || this.form.questionType=='MULTIPLE' ||
          this.form.questionTypeChildren=='MULTI' || this.form.questionTypeChildren=='MULTIPLE'
        ){
          for (let i=0; i< this.form.optionsList.length; i++){
            this.form.correctAnswerList.push(false);
          }
        }
      } else if(this.form.questionType=='GAPFILL' || this.form.questionTypeChildren=='GAPFILL') {
          this.form.gapFillAnswerList = [
            {
              label: 1,
              content: null,
            },
          ];
          this.form.optionsList = [];
          this.form.correctAnswerList = [];
        } else {
          this.form.gapFillAnswerList = [];
          this.form.optionsList = [];
          this.form.correctAnswerList = [];
        }
        
    },
    getKnowledgePointTreeselect() {
      listKnowledgePoint().then(response => {
        this.knowledgePointOptions = this.convertTree(this.handleTree(response.data, "kpId", "parentId"));
      })
    },
    convertTree(knowledgePointArray) {
      if (!Array.isArray(knowledgePointArray)) return [];
      return knowledgePointArray.map(item => {
        // 提取需要转换的字段，其余字段保留
        const { kpId, kpName, children, ...rest } = item;
        const newNode = {
          value: kpId,
          label: kpName,
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
        questionId: null,
        categoryId: null,
        questionType: null,
        questionTypeChildren: null,
        questionContent: null,
        optionsList: [],
        gapFillAnswerList: [],
        correctAnswerList: [],
        correctAnswer: null,
        analysis: null,
        difficulty: null,
        examFrequency: null,
        kpIds: null,
        materialContent: null,
        status: null,
        delFlag: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null,
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
      this.ids = selection.map(item => item.questionId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    //材料列表
    getListQuestionData(){
      listQuestionData().then(response => {
        this.questionDataList = response.rows;
      })
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.getListQuestionData();
      this.open = true
      this.title = "添加题库题目"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      this.getListQuestionData();
      const questionId = row.questionId || this.ids
      getQuestion(questionId).then(response => {
        console.log(response)
        this.form = response.data
        this.open = true
        this.title = "修改题库题目"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.questionId != null) {
            updateQuestion(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addQuestion(this.form).then(response => {
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
      const questionIds = row.questionId || this.ids
      this.$modal.confirm('是否确认删除题库题目编号为"' + questionIds + '"的数据项？').then(function() {
        return delQuestion(questionIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('hawkeye/question/export', {
        ...this.queryParams
      }, `question_${new Date().getTime()}.xlsx`)
    },
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "产品导入";
      this.upload.open = true;
    },
    /** 下载模板操作 */
    importTemplate() {
      importTemplate("/product/product/importTemplate").then(response => {
        this.download(response.msg);
      });
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      // 使用官方建议的方法清空文件列表，替换原来的this.$refs.upload.clearFiles()
      this.$refs.uploadRef.handleRemove(file, fileList);
      this.$alert(response.msg, "导入结果", { dangerouslyUseHTMLString: true });
      this.getList();
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.uploadRef.submit();
    },
  }
}
</script>
