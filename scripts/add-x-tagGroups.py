import yaml
import os
from collections import OrderedDict

DOCS_DIR = 'src/main/resources/static/docs'
API_DOC_FILENAME_TXT = 'scripts/api-doc-filename.txt'

def get_api_doc_filename():
    if not os.path.exists(API_DOC_FILENAME_TXT):
        return None
    with open(API_DOC_FILENAME_TXT, encoding='utf-8') as f:
        return f.read().strip()

api_doc_file = get_api_doc_filename()
files = [os.path.join(DOCS_DIR, 'latest.yaml')]
if api_doc_file:
    files.append(os.path.join(DOCS_DIR, api_doc_file))

tag_groups = [
    {
        "name": "기초 데이터",
        "tags": [
            "기초 데이터 API V1",
            "기초-건강 관리 현황 API V1",
            "기초-주거 자립 현황 API V1",
            "기초-보조기기 사용 현황 API V1",
            "기초-사회망 현황 API V1",
            "기초-편의 시설 제공 현황 API V1",
            "기초-진로 교육 현황 API V1",
            "기초-고용 현황 API V1"
        ]
    },
    {
        "name": "POI API",
        "tags": [
            "이동형 POI API V1"
        ]
    },
    {
        "name": "고용 관련 API",
        "tags": [
            "장애인 고용 관련 데이터 API V1"
        ]
    }
]

for file in files:
    if not os.path.exists(file):
        continue
    with open(file, encoding='utf-8') as f:
        data = yaml.safe_load(f)
    data.pop('x-tagGroups', None)

    # 실제 tags 배열의 name 값만 추출
    valid_tags = set()
    for tag in data.get('tags', []):
        if isinstance(tag, dict) and 'name' in tag:
            valid_tags.add(tag['name'])

    # tag_groups의 tags 값 중 실제 tags 배열에 없는 태그는 제거
    filtered_tag_groups = []
    for group in tag_groups:
        filtered_tags = [t for t in group['tags'] if t in valid_tags]
        filtered_tag_groups.append({"name": group['name'], "tags": filtered_tags})

    new_data = OrderedDict()
    for key in ['openapi', 'info', 'tags', 'x-tagGroups', 'servers', 'security', 'paths', 'components']:
        if key == 'x-tagGroups':
            new_data[key] = filtered_tag_groups
        elif key in data:
            new_data[key] = data[key]
    for key in data:
        if key not in new_data:
            new_data[key] = data[key]
    # 최상위만 dict로 변환
    with open(file, 'w', encoding='utf-8') as f:
        yaml.dump(dict(new_data), f, allow_unicode=True, sort_keys=False, Dumper=yaml.SafeDumper, default_flow_style=False)
    print(f'x-tagGroups가 {file}에 root 최상단에 추가되었습니다. (불일치 태그 자동 제거)') 