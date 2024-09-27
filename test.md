```ahk
#Persistent
CoordMode, Mouse, Screen   ; Coordinates relative to the entire screen
SetMouseDelay, 200         ; 200 ms delay between mouse clicks

; Pre-defined coordinates for clicking (example coordinates)
clickCoords := [[100, 200], [200, 300], [300, 400], [400, 500]]

scrollInterval := 60000    ; 1 minute in milliseconds
lastScrollTime := A_TickCount ; Record startup time
scrollDirection := 0       ; 0 = Scroll Down, 1 = Scroll Up

Loop
{
    ; Click all the pre-defined coordinates
    for each, coords in clickCoords
    {
        MouseClick, Left, % coords.1, % coords.2
        Sleep, 200 ; 200 ms delay between each click
    }

    ; Check if 1 minute has passed since last scroll or script start
    if (A_TickCount - lastScrollTime > scrollInterval)
    {
        if (scrollDirection = 0)
        {
            Send {WheelDown 20}  ; Scroll down 20 lines
            scrollDirection := 1 ; Next time, scroll up
        }
        else
        {
            Send {WheelUp 20}    ; Scroll up 20 lines
            scrollDirection := 0 ; Next time, scroll down
        }

        lastScrollTime := A_TickCount ; Reset the scroll time
    }
}
```
