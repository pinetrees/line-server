from flask import Flask, render_template, Session
app = Flask(__name__)
app.debug = True

@app.route('/')
def index():
    return 'Salsify Line Server'

@app.route('/lines/<index>')
def lines(index=None):
    i = int(index)
    print file_length
    if file_length <= i:
        return 'Index out of range', 413
    else:
        line = lines[i]
        return render_template('lines.html', line=line), 413

if __name__ == '__main__':
    f = open('file.txt', 'r')
    lines = []
    for line in f:
        lines.append(line)
    file_length = len(lines)
    app.run()
