package com.example.shopping_list_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopping_list_app.ui.theme.Shopping_list_appTheme

data class ShoppingItem(val name: String, val quantityOrSize: String, val bought: Boolean, val emoji: String)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Shopping_list_appTheme {
                ShoppingListScreen()
            }
        }
    }
}

@Composable
fun ShoppingListScreen() {
    var items by remember { mutableStateOf(listOf<ShoppingItem>()) }
    var newItemName by remember { mutableStateOf("") }
    var newItemQuantityOrSize by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            TextField(
                value = newItemName,
                onValueChange = { newItemName = it },
                label = { Text("Item Name") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))

            TextField(
                value = newItemQuantityOrSize,
                onValueChange = { newItemQuantityOrSize = it },
                label = { Text("Quantity / Size") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))

            Button(onClick = {
                if (newItemName.isNotEmpty() && newItemQuantityOrSize.isNotEmpty()) {
                    items = items + ShoppingItem(newItemName, newItemQuantityOrSize, false, "❌")
                    newItemName = ""
                    newItemQuantityOrSize = ""
                }
            }) {
                Text("Add")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(items) { item ->
                ShoppingListItem(
                    item = item,
                    onItemChecked = { checked ->
                        items = items.map {
                            if (it == item) it.copy(bought = checked, emoji = if (checked) "✅" else "❌") else it
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun ShoppingListItem(item: ShoppingItem, onItemChecked: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Row {
                Text(text = item.emoji,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    textDecoration = if (item.bought) TextDecoration.LineThrough else TextDecoration.None)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = item.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    textDecoration = if (item.bought) TextDecoration.LineThrough else TextDecoration.None)
            }
            Text(text = "Quantity/Size: ${item.quantityOrSize}",
                style = MaterialTheme.typography.bodySmall,
                textDecoration = if (item.bought) TextDecoration.LineThrough else TextDecoration.None)
        }
        Checkbox(checked = item.bought, onCheckedChange = onItemChecked)
    }
}

@Preview(showBackground = true)
@Composable
fun ShoppingListPreview() {
    Shopping_list_appTheme {
        ShoppingListScreen()
    }
}
