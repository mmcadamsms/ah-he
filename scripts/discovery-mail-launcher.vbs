Option Explicit

If WScript.Arguments.Count <> 4 Then WScript.Quit 2

Dim shell, command, index, value, result
Set shell = CreateObject("WScript.Shell")
For index = 0 To 3
    value = WScript.Arguments(index)
    If InStr(value, Chr(34)) > 0 Or InStr(value, vbCr) > 0 Or InStr(value, vbLf) > 0 Or InStr(value, "%") > 0 Then WScript.Quit 2
Next

command = QuoteArgument(WScript.Arguments(0)) & " -NoProfile -NonInteractive -STA -WindowStyle Hidden -File " _
    & QuoteArgument(WScript.Arguments(1)) & " -Action Run -StateDirectory " _
    & QuoteArgument(WScript.Arguments(2)) & " -TaskName " _
    & QuoteArgument(WScript.Arguments(3))
result = shell.Run(command, 0, True)
WScript.Quit result

Function QuoteArgument(ByVal value)
    Dim suffix
    suffix = ""
    ' Native PowerShell parsing, unlike WScript arguments, escapes a closing quote after a backslash.
    Do While Right(value, 1) = "\"
        suffix = suffix & "\\"
        value = Left(value, Len(value) - 1)
    Loop
    QuoteArgument = Chr(34) & value & suffix & Chr(34)
End Function
