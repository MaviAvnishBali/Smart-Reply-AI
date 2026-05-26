package com.myai.smartreplyai.features.templates

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myai.smartreplyai.domain.model.Template
import com.myai.smartreplyai.domain.repository.TemplateRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TemplatesUiState(val templates: List<Template> = emptyList())

@HiltViewModel
class TemplatesViewModel @Inject constructor(
    private val templateRepository: TemplateRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TemplatesUiState())
    val uiState: StateFlow<TemplatesUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            templateRepository.observeTemplates().collect { list ->
                _uiState.update { it.copy(templates = list) }
            }
        }
    }

    fun add(title: String, content: String, keywords: String) {
        viewModelScope.launch {
            templateRepository.saveTemplate(
                Template(
                    id = 0,
                    title = title,
                    content = content,
                    category = "Custom",
                    keywords = keywords.split(",").map { it.trim() }.filter { it.isNotEmpty() },
                    usageCount = 0,
                    createdAt = System.currentTimeMillis()
                )
            )
        }
    }

    fun delete(template: Template) {
        viewModelScope.launch {
            templateRepository.deleteTemplate(template)
        }
    }
}
