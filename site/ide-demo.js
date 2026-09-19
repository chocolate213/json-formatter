const editor = document.querySelector('#json');
const sample = {
  project: 'Orbit demo',
  environment: 'development',
  version: 2,
  active: true,
  services: [
    {
      name: 'gateway',
      port: 8080,
      health: 'passing',
      routes: ['/status', '/search']
    },
    {
      name: 'worker',
      concurrency: 4,
      tasks: ['format', 'validate', 'export']
    }
  ]
};
function emit(message, error = false) {
  parent.postMessage({type:'json-demo-status',message,error},location.origin);
}
function escapeHTML(text) {
  return text.replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;');
}
function render() {
  const tokens = /"(?:\\.|[^"\\])*"(?=\s*:)|"(?:\\.|[^"\\])*"|\b(?:true|false|null)\b|-?\b\d+(?:\.\d+)?(?:[eE][+-]?\d+)?\b/g;
  let highlighted = '', last = 0;
  for (const match of editor.value.matchAll(tokens)) {
    highlighted += escapeHTML(editor.value.slice(last,match.index));
    const token = match[0];
    const type = token.startsWith('"') ? (/^\s*:/.test(editor.value.slice(match.index+token.length)) ? 'field' : 'string') : (/^(true|false|null)$/.test(token) ? 'keyword' : 'number');
    highlighted += `<span class="tok-${type}">${escapeHTML(token)}</span>`;
    last = match.index+token.length;
  }
  highlighted += escapeHTML(editor.value.slice(last));
  document.querySelector('#json-rows').innerHTML = highlighted.split('\n').map((line,i)=>`<div class="ide-editor__row"><div class="ide-editor__ln">${i+1}</div><div class="ide-editor__gutter"></div><div class="ide-editor__code">${line || ' '}</div></div>`).join('');
  syncScroll();
}
function syncScroll() {
  const layer = document.querySelector('#json-render');
  layer.scrollTop = editor.scrollTop;
  layer.scrollLeft = editor.scrollLeft;
}
function reset() {editor.value=JSON.stringify(sample,null,2);render();emit('Ready when you are.');}
async function apply(op) {
  try {
    if(op==='copy') {
      try {await navigator.clipboard.writeText(editor.value);emit('Copied to clipboard.');}
      catch {emit('Select the text and copy it manually.',true);}
      return;
    }
    let result=editor.value;
    if(op==='format')result=JSON.stringify(JSON.parse(result),null,2);
    if(op==='minify')result=JSON.stringify(JSON.parse(result));
    if(op==='validate')JSON.parse(result);
    if(op==='escape')result=JSON.stringify(result).slice(1,-1);
    if(op==='unescape')result=JSON.parse('"'+result+'"');
    editor.value=result;render();
    emit({format:'Formatted. Room to breathe.',minify:'Minified. Same data, less space.',validate:'✓ Valid JSON.',escape:'Escaped. Ready to embed.',unescape:'Unescaped. Readable again.'}[op]);
  } catch {emit('Check your JSON or escaped string. Your input is unchanged.',true);}
}
document.querySelectorAll('[data-op]').forEach(button=>button.addEventListener('click',()=>apply(button.dataset.op)));
editor.addEventListener('input',()=>{render();emit('Edited. Choose an action.');});
editor.addEventListener('scroll',syncScroll);
window.addEventListener('message',event=>{
  if(event.origin!==location.origin || event.source!==parent || event.data?.type!=='json-demo-action')return;
  if(event.data.action==='reset')reset();
  else if(['format','minify','validate','escape','unescape'].includes(event.data.action))apply(event.data.action);
});
reset();
