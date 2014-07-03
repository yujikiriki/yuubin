#Yuubin
Simple embeddable send-only SMTP server written in Scala. 
Heavily based and inspired on [Aspirin](https://github.com/masukomi/aspirin) and [Courier](https://github.com/softprops/courier) projects.

# Features
* Simple to use. Embeddable.
* Async email sending using futures.
* Sync email sending for non concurrent scenarios.
* Complete SMTP error handling and reporting.
* Uses the DNS MX record with the highest priority.
* Looks up the MX records through the hierarchy of subdomains.

# License
The MIT License (MIT)

Copyright (c) 2014 Yuji Kiriki

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
