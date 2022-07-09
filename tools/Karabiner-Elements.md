## Reference Links
- [Official Website Home Page](https://karabiner-elements.pqrs.org/)
- [Basic config document](https://karabiner-elements.pqrs.org/docs/manual/)
- [Complex Config Document](https://karabiner-elements.pqrs.org/docs/json/)

## My configurations
### Swap command & control
- I use Karabiner elements instead of mac os keyboard setting to achieve this because Karabiner elements would override mac os keyboard remap
- This is unavoidable according to [Overwriting key remapping of macOS settings. · Issue #35 · pqrs-org/Karabiner-Elements (github.com)](https://github.com/pqrs-org/Karabiner-Elements/issues/35)
- The following rules are added manually in Karabiner GUI's "Simple modifications" tab

```json
{
    "simple_modifications":
    [
        {
            "from":
            {
                "key_code": "left_control"
            },
            "to":
            [
                {
                    "key_code": "left_command"
                }
            ]
        },
        {
            "from":
            {
                "key_code": "left_command"
            },
            "to":
            [
                {
                    "key_code": "left_control"
                }
            ]
        },
        {
            "from":
            {
                "key_code": "right_control"
            },
            "to":
            [
                {
                    "key_code": "right_command"
                }
            ]
        },
        {
            "from":
            {
                "key_code": "right_command"
            },
            "to":
            [
                {
                    "key_code": "right_control"
                }
            ]
        }
    ]
}
```

### Enable left window key in citrix workspace

- The rule is written in a json file
- The file should be stored in path `~/.config/karabiner/assets/complex_modifications/citrix_workspace.json`
	- After saving to this location, you can enable it by clicking `Add rule` in tab `Complex modifications`

```javascript
{
    "title": "Citrix Workspace", // This would be display name of the rule group in add rule popup screen
    "rules":
    [
        {
            "description": "Enable left windows key", // This would be display name of the rule in add rule popup screen
            "manipulators":
            [
                {
                    "type": "basic",
                    "from":
                    {
                        "key_code": "left_control", // The left windows key is re-mapped to left control in previous `Swap command & control` config
                        "modifiers": {
                          "optional": ["any"] // When any modifier is pressed at the same time, keep it. You can check doc https://karabiner-elements.pqrs.org/docs/json/complex-modifications-manipulator-definition/from/modifiers/ for more detail
                        }
                    },
                    "to":
                    [
                        {
                            "key_code": "right_command" // In citrix workspace conf, right command key is mapped to windows key
                        }
                    ],
                    "conditions":
                    [
                        {
                            "type": "frontmost_application_if", // Only if this is the frontmost application
                            "bundle_identifiers":
                            [
                                "^com\\.citrix\\.receiver\\.icaviewer\\.mac$" // This is regex expression. The bundle identifier is retrieved from "Frontmost Application" tab of "Karabiner-EventViewer"
                            ]
                        }
                    ]
                }
            ]
        }
    ]
}
```