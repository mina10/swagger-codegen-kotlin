package com.teamlab.smartphone.codegen

import io.swagger.codegen.*
import io.swagger.codegen.CodegenParameter
import io.swagger.codegen.languages.kotlin.KotlinClientCodegen
import io.swagger.v3.oas.models.media.Schema

class KotlinCodegen : KotlinClientCodegen() {
    override fun getTag() = CodegenType.CLIENT
    override fun getName() = "kotlin"
    override fun getHelp() = "Generate a Kotlin client."
    override fun getTemplateVersion() = "3.0.0-rc1"

    init{
       apiTestTemplateFiles.clear()
       modelTestTemplateFiles.clear()
       modelDocTemplateFiles.clear()
       apiDocTemplateFiles.clear()
       templateDir = "src/main/resources/3.0.0-rc1/kotlin-client"
       
       (defaultIncludes as MutableSet) += setOf(
                "Byte",
                "Short",
                "Int",
                "Long",
                "Float",
                "Double",
                "Boolean",
                "Char",
                "Array",
                "List",
                "Set",
                "Map"
        )
        (languageSpecificPrimitives as MutableSet) += setOf(
                "Any",
                "Byte",
                "Short",
                "Int",
                "Long",
                "Float",
                "Double",
                "Boolean",
                "Char",
                "String",
                "Array",
                "List",
                "Map",
                "Set")
        (typeMapping as MutableMap) += mapOf(
                "integer" to "Int",
                "long" to "Long",
                "float" to "Float",
                "double" to "Double",
                "string" to "String",
                "byte" to "Byte",
                "binary" to "ByteArray",
                "boolean" to "Boolean",
                "date" to "Date",
                "dateTime" to "Date",
                "password" to "String",
                "array" to "List",
                "map" to "Map",
                "uuid" to "UUID")
    }

    override fun fromModel(name: String, schema: Schema<Any>, allDefinitions: MutableMap<String, Schema<Any>>): CodegenModel {
        return super.fromModel(name, schema, allDefinitions).apply {
            imports.remove("ApiModelProperty")
            imports.remove("ApiModel")
        }
    }

    override fun postProcessOperations(objs: MutableMap<String, Any>): MutableMap<String, Any> {
        super.postProcessOperations(objs)
        @Suppress("UNCHECKED_CAST")
        (objs["operations"] as? Map<String, Any>)?.let {
            (it["operation"] as? List<CodegenOperation>)?.forEach {
                it.path = it.path.removePrefix("/")
            }
        }
        return objs
    }

    override fun processOpts() {
        super.processOpts()
        supportingFiles.clear()
    }
}
