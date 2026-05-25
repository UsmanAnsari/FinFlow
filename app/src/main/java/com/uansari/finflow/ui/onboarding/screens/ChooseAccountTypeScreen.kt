package com.uansari.finflow.ui.onboarding.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.uansari.finflow.ui.components.OnboardingScaffold

private val accountTypes = listOf(
    "Personal" to "Everyday spending and savings",
    "Business" to "For freelancers and companies",
    "Student" to "Fee-free banking for students"
)

@Composable
fun ChooseAccountTypeScreen(
    selectedType: String, onAccountTypeSelected: (String) -> Unit
) {
    var localSelected by remember { mutableStateOf(selectedType) }

    OnboardingScaffold(
        title = "Choose account type",
        subtitle = "You can always change this later.",
        progress = 0.25f,
        content = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                accountTypes.forEach { (type, description) ->
                    val isSelected = localSelected == type
                    Card(
                        onClick = { localSelected = type },
                        modifier = Modifier.fillMaxWidth(),
                        border = if (isSelected) BorderStroke(
                            2.dp, MaterialTheme.colorScheme.primary
                        ) else null,
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer
                            else MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = type,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = description,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            if (isSelected) {
                                Icon(
                                    imageVector = Icons.Rounded.CheckCircle,
                                    contentDescription = "Selected",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }
            }
        },
        bottomContent = {
            Button(
                onClick = { if (localSelected.isNotEmpty()) onAccountTypeSelected(localSelected) },
                enabled = localSelected.isNotEmpty(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Continue")
            }
        })
}
