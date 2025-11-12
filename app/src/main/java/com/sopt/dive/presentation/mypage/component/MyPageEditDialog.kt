import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.sopt.dive.core.designsystem.component.DiveSoptTextField

@Composable
fun MyPageEditDialog(
    onDismiss: () -> Unit,
    onConfirm: (name: String, email: String, age: String) -> Unit
) {
    val name = rememberTextFieldState()
    val email = rememberTextFieldState()
    val age = rememberTextFieldState()

    Dialog(onDismissRequest = { onDismiss() }) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            tonalElevation = 8.dp,
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "정보 수정",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(16.dp))

                DiveSoptTextField(
                    state = name,
                    placeholder = "이름",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                DiveSoptTextField(
                    state = email,
                    placeholder = "이메일",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                DiveSoptTextField(
                    state = age,
                    placeholder = "나이",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = { onDismiss() }) {
                        Text("취소")
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = {
                            onConfirm(name.text.toString(), email.text.toString(), age.text.toString())
                            onDismiss()
                        }
                    ) {
                        Text("확인")
                    }
                }
            }
        }
    }
}
