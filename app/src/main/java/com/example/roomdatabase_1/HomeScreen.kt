package com.example.roomdatabase_1

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.roomdatabase_1.data.User


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: UserViewModel) {

    val users = viewModel.viewList.collectAsState()

    var newUserName = remember{ mutableStateOf("")}
    var showUpdateDialog = remember{ mutableStateOf(false) }
    var userToUpdate = remember{ mutableStateOf<User?>(null) }
    var updateName = remember{ mutableStateOf("") }
    var showAddDialog = remember{ mutableStateOf(false) }
    var userToAdd = remember{ mutableStateOf<User?>(null) }

    if(showAddDialog.value){
        AlertDialog(
            onDismissRequest = {
                showAddDialog.value = false
                userToAdd.value = null
            },
            title = { Text(text = "Add User")},
            text = {
                TextField(
                    value = newUserName.value,
                    onValueChange = {newUserName.value = it},
                    label = { Text(text = "New User Name")},
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (newUserName.value.isNotBlank()){
                            viewModel.addUser(newUserName.value)
                            showAddDialog.value = false
                            newUserName.value = ""
                        }
                    }) {
                    Text(text = "Add")
                }
            },
            dismissButton ={
                TextButton(
                    onClick = { 
                        showUpdateDialog.value = false
                        newUserName.value = ""
                    }) {
                    Text(text = "Cancel")
                }
            }
        )
    }

    if(showUpdateDialog.value && userToUpdate.value != null){
        AlertDialog(
            onDismissRequest = {
                showUpdateDialog.value = false
                userToUpdate.value = null
            },
            title = { Text(text = "Update User")},
            text = {
                Text(text = "Enter a new name")
                TextField(
                    value = updateName.value,
                    onValueChange = {updateName.value = it},

                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                         userToUpdate.value?.let {
                             users->
                             viewModel.updateUser(users, updateName.value)
                         }
                        showUpdateDialog.value = false
                        userToUpdate.value = null
                    }) {
                    Text(text = "Update")
                }
            },
            dismissButton ={
                TextButton(
                    onClick = {
                        showUpdateDialog.value = false
                        userToUpdate.value = null
                    }) {
                    Text(text = "Cancel")
                }
            }
        )
    }
    
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "User Name") })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showAddDialog.value = true
                }
            ) {
                Icon(imageVector = Icons.Default.Add,
                    contentDescription = null)
            }
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(start = 16.dp, end = 16.dp)
            ) {
                if(users.value.isNotEmpty()){
                    LazyColumn(
                        modifier = Modifier.weight(1f)
                    ) {
                        items(
                            items = users.value
                        ){user ->
                            Row (modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ){
                                Text(text = user.name, style = MaterialTheme.typography.bodyLarge)
                                Row {
                                    IconButton(
                                        onClick = {
                                            userToUpdate.value = user
                                            updateName.value = user.name
                                            showUpdateDialog.value = true
                                        }
                                    ) {
                                        Icon(Icons.Filled.Edit, contentDescription = null)
                                    }

                                    IconButton(
                                        onClick = {
                                            viewModel.deleteUser(user)
                                        }
                                    ) {
                                        Icon(Icons.Filled.Delete, contentDescription = null)
                                    }
                                }
                            }
                            HorizontalDivider()
                        }
                    }
                }else{
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.sad),
                            contentDescription = null,
                            modifier = Modifier.size(100.dp))
                     Text(text = "No user name available")
                    }
                }

            }
        }
    )
}