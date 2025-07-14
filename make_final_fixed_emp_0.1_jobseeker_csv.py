import csv

input_path = "docs/1. db_한국장애인고용공단_장애인 구직자 현황_20241231.CSV"
output_path = "docs/1. db_한국장애인고용공단_장애인 구직자 현황_20241231-최종fix.csv"

def clean_job_type(job_type):
    job_type = job_type.strip()
    # 쌍따옴표로 감싸져 있으면
    if job_type.startswith('"') and job_type.endswith('"'):
        inner = job_type[1:-1].replace('"', '""')
        # 내부에 쉼표나 '·'가 있으면 쌍따옴표 유지
        if ',' in inner or '·' in inner:
            return f'"{inner}"'
        else:
            return inner
    else:
        escaped = job_type.replace('"', '""')
        if ',' in job_type or '·' in job_type:
            return f'"{escaped}"'
        else:
            return escaped

def csv_escape(field):
    # 이미 쌍따옴표로 감싸진 필드는 그대로 둠
    if field.startswith('"') and field.endswith('"'):
        return field
    if any(c in field for c in [',', '"', '\n', '\r']):
        return '"' + field.replace('"', '""') + '"'
    else:
        return field

with open(input_path, "r", encoding="euc-kr") as infile, \
     open(output_path, "w", encoding="euc-kr", newline="") as outfile:
    reader = csv.reader(infile)
    for row in reader:
        if len(row) != 10:
            continue  # 컬럼이 10개가 아닌 행은 건너뜀
        head = row[:4]
        job_type = row[4]
        tail = row[5:]
        fixed_job_type = clean_job_type(job_type)
        # job_type만 별도 처리, 나머지는 csv_escape
        fields = [csv_escape(str(f)) for f in head] + [fixed_job_type] + [csv_escape(str(f)) for f in tail]
        line = ','.join(fields)
        outfile.write(line + '\n') 