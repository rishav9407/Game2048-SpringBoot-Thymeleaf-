// fetch state and render, handle input
(async function(){
  const boardEl = document.getElementById('board');
  const scoreEl = document.getElementById('score');
  const restartBtn = document.getElementById('restartBtn');
  const messages = document.getElementById('messages');

  async function fetchState() {
    const res = await fetch('/state');
    return await res.json();
  }

  function renderGrid(state) {
    const size = state.size;
    boardEl.dataset.size = size;
    boardEl.style.gridTemplateColumns = `repeat(${size}, 1fr)`;
    boardEl.innerHTML = '';
    state.grid.flat().forEach(v => {
      const cell = document.createElement('div');
      cell.classList.add('cell');
      cell.textContent = v === 0 ? '' : v;
      boardEl.appendChild(cell);
    });
    scoreEl.textContent = state.score;
    if (state.won) messages.innerText = "You reached 2048! 🎉";
    else if (state.gameOver) messages.innerText = "Game Over — no moves left.";
    else messages.innerText = "";
  }

  async function load() {
    const s = await fetchState();
    renderGrid(s);
  }

  async function doMove(dir) {
    const res = await fetch('/move?dir=' + dir, {method:'POST'});
    const state = await res.json();
    renderGrid(state);
  }

  document.addEventListener('keydown', (e) => {
    const map = { ArrowLeft: 'LEFT', ArrowRight: 'RIGHT', ArrowUp: 'UP', ArrowDown: 'DOWN' };
    if (map[e.key]) { doMove(map[e.key]); e.preventDefault(); }
  });

  restartBtn.addEventListener('click', async () => {
    const res = await fetch('/restart', {method:'POST'});
    const state = await res.json();
    renderGrid(state);
  });

  // mobile/on-screen controls
  document.querySelectorAll('[data-dir]').forEach(btn => {
    btn.addEventListener('click', () => doMove(btn.dataset.dir));
  });

  // initial load
  await load();
})();
