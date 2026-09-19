const frame = document.querySelector('#ide-frame');
function action(value) {
  frame.contentWindow.postMessage({ type: 'json-demo-action', action: value }, location.origin);
}
window.addEventListener('message', event => {
  if (event.origin !== location.origin || event.source !== frame.contentWindow || event.data?.type !== 'json-demo-status') return;
  const status = document.querySelector('#status');
  status.textContent = event.data.message;
  status.parentElement.classList.toggle('error', event.data.error);
});
let step = 0;
const captions = [
  'Paste a response into the tool window. Keep your project exactly where it is.',
  'Minify compacts the payload. Your source file stays untouched.',
  'Expand Json restores indentation. Copy the result without creating a file.'
];
function updateTour() {
  document.querySelector('#tour-step').textContent = `STEP ${step + 1} / 3`;
  document.querySelector('#tour-caption').textContent = captions[step];
  document.querySelector('#tour-next').textContent = ['Next: minify JSON →', 'Next: format JSON →', 'Replay walkthrough ↺'][step];
}
document.querySelector('#reset').addEventListener('click', () => { action('reset'); step = 0; updateTour(); });
document.querySelector('#tour-next').addEventListener('click', () => { step = (step + 1) % 3; action(['reset', 'minify', 'format'][step]); updateTour(); });

const reduced = matchMedia('(prefers-reduced-motion: reduce)');
const hero = document.querySelector('.hero');
const art = document.querySelector('.flow-art');
hero.addEventListener('pointermove', event => {
  if (reduced.matches || event.pointerType === 'touch') return;
  const rect = hero.getBoundingClientRect();
  art.style.setProperty('--art-x', `${((event.clientX - rect.left) / rect.width - .5) * 20}px`);
  art.style.setProperty('--art-y', `${((event.clientY - rect.top) / rect.height - .5) * 16}px`);
});
hero.addEventListener('pointerleave', () => {
  art.style.setProperty('--art-x', '0px'); art.style.setProperty('--art-y', '0px');
});
for (const card of document.querySelectorAll('.op-card')) {
  card.addEventListener('pointermove', event => {
    if (reduced.matches || event.pointerType === 'touch') return;
    const rect = card.getBoundingClientRect();
    card.style.setProperty('--spot-x', `${event.clientX - rect.left}px`);
    card.style.setProperty('--spot-y', `${event.clientY - rect.top}px`);
  });
  card.addEventListener('pointerleave', () => {
    card.style.removeProperty('--spot-x'); card.style.removeProperty('--spot-y');
  });
}
if ('IntersectionObserver' in window) {
  document.documentElement.classList.add('motion-ready');
  const observer = new IntersectionObserver(entries => {
    for (const entry of entries) if (entry.isIntersecting) {
      entry.target.classList.add('visible'); observer.unobserve(entry.target);
    }
  }, { threshold: .08 });
  document.querySelectorAll('.reveal').forEach(element => observer.observe(element));
}
const progress = document.querySelector('.reading-progress');
let scheduled = false;
function updateProgress() {
  const maxScroll = document.documentElement.scrollHeight - innerHeight;
  progress.style.transform = `scaleX(${maxScroll > 0 ? Math.min(1, Math.max(0, scrollY / maxScroll)) : 0})`;
  scheduled = false;
}
function scheduleProgress() {
  if (!scheduled) { scheduled = true; requestAnimationFrame(updateProgress); }
}
addEventListener('scroll', scheduleProgress, { passive: true });
addEventListener('resize', scheduleProgress);
updateProgress();
