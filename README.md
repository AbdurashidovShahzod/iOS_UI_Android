# IOS UI for Android

iOS UI components for android developers...

## Installation

Use the package manager [](https://pip.pypa.io/en/stable/) to install ios UI.

```bash
implementation
```

## Usage


#  Ios Dialog

<img src="https://user-images.githubusercontent.com/61906391/214827156-fcb69157-dc10-4da9-8c6f-d23372247f7c.gif" width="350" height="650"/>


```kotlin
  binding.iosDialog.setOnClickListener {
            IOSDialog.Builder(this)
                .message("Ios dialog...")
                .enableAnimation(true)
                .build()
                .show())
```


### Ios title Dialog



```kotlin
 binding.iosDialogTitle.setOnClickListener {
            IOSDialog.Builder(this)
                .message("App would like to send your notifications")
                .enableAnimation(true)
                .positiveButtonText("Allow")
                .negativeButtonText("Don't allow")
                .build()
                .show()
        }
```


### Ios options Dialog



```kotlin
 binding.iosDialogOptions.setOnClickListener {
            val list = arrayListOf<IOSDialogButton>().apply {
                add(IOSDialogButton(1, "User registration", true, IOSDialogButton.TYPE_POSITIVE))
                add(IOSDialogButton(2, "User replace", true, IOSDialogButton.TYPE_POSITIVE))
                add(IOSDialogButton(3, "User delete", true, IOSDialogButton.TYPE_POSITIVE))
                add(IOSDialogButton(4, "Log out", true, IOSDialogButton.TYPE_NEGATIVE))
            }
            IOSDialog.Builder(this)
                .title("Welcome User")
                .message("Are you ready to change your data?")
                .enableAnimation(true)
                .multiOptions(true)
                .multiOptionsListeners { iosDialog, iosDialogButton ->
                    iosDialog.dismiss()
                    when (iosDialogButton.id) {
                        1 -> it.showSnackBar("User registration")
                        2 -> it.showSnackBar("User replace")
                        3 -> it.showSnackBar("User delete")
                        4 -> it.showSnackBar("Log out")
                    }
                }
                .iosDialogButtonList(list)
                .build()
                .show()
        }
```




#  Ios Switch

<img src="https://user-images.githubusercontent.com/61906391/214825207-7449e1d5-e02d-4639-9a3e-8409780ca82a.gif " width="350" height="650"/>



## Usage



xml file -> 


```kotlin
 <uz.shahzod.unzosoft.iosui_android.ui.iosSwitch.IosSwitch
            android:id="@+id/ios_switch"
            android:layout_width="55dp"
            android:layout_height="match_parent" />
```


```kotlin
 binding.iosSwitch.setOnCheckedChangeListener(object : IosSwitch.OnCheckedChangeListener {
            override fun onCheckedChanged(switchView: IosSwitch?, isChecked: Boolean) {
                switchView?.showSnackBar(isChecked.toString())
            }
        })
```


#  Ios Search bar

<img src="https://user-images.githubusercontent.com/61906391/214826835-c7eba066-63c4-478d-9b09-7707abb93dd1.gif " width="350" height="650"/>


## Usage



xml file -> 


```kotlin
 <uz.shahzod.unzosoft.iosui_android.ui.searchBar.IosSearchBar
                style="@style/IosSearchBarStyle"
                android:id="@+id/ios_search" />
```


```kotlin
  binding.iosSearch.setOnSearchClickListener(object :IosSearchBar.OnSearchClickListener{
            override fun onSearchClick(view: View?) {
                view?.showSnackBar("Searching....")
            }
        })
```






## Contributing

Pull requests are welcome. For major changes, please open an issue first
to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License

[MIT](https://choosealicense.com/licenses/mit/)
