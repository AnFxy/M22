package com.nonetxmxy.mmzqfxy.service

import com.nonetxmxy.mmzqfxy.model.BaseResponse
import com.nonetxmxy.mmzqfxy.model.response.FileResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface IFileUploadService {

    // 上传文件
    @POST(NetPaths.uploadFile)
    suspend fun uploadFile(@Body maps: HashMap<String, String>): BaseResponse<FileResponse>
}