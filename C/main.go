package main

import (
	"fmt"
	"sync"
)

// Граф представляє собою структуру для збереження міст і рейсів між ними.
type Graph struct {
	mu     sync.RWMutex
	cities map[string]bool           // Мапа для зберігання міст
	routes map[string]map[string]int // Мапа для зберігання рейсів і цін
}

// NewGraph створює новий граф.
func NewGraph() *Graph {
	return &Graph{
		cities: make(map[string]bool),
		routes: make(map[string]map[string]int),
	}
}

// SetTicketPrice встановлює ціну квитка для рейсу між двома містами.
func (g *Graph) SetTicketPrice(cityA, cityB string, price int) {
	g.mu.Lock()
	defer g.mu.Unlock()
	g.routes[cityA][cityB] = price
	g.routes[cityB][cityA] = price
}

// AddRoute додає новий рейс між двома містами з вказаною ціною.
func (g *Graph) AddRoute(cityA, cityB string, price int) {
	g.mu.Lock()
	defer g.mu.Unlock()
	if g.routes[cityA] == nil {
		g.routes[cityA] = make(map[string]int)
	}
	if g.routes[cityB] == nil {
		g.routes[cityB] = make(map[string]int)
	}
	g.routes[cityA][cityB] = price
	g.routes[cityB][cityA] = price
}

// DeleteRoute видаляє рейс між двома містами.
func (g *Graph) DeleteRoute(cityA, cityB string) {
	g.mu.Lock()
	defer g.mu.Unlock()
	delete(g.routes[cityA], cityB)
	delete(g.routes[cityB], cityA)
}

// AddCity додає нове місто до графа.
func (g *Graph) AddCity(city string) {
	g.mu.Lock()
	defer g.mu.Unlock()
	g.cities[city] = true
}

// DeleteCity видаляє місто з графа, включаючи всі рейси, пов'язані з цим містом.
func (g *Graph) DeleteCity(city string) {
	g.mu.Lock()
	defer g.mu.Unlock()
	delete(g.cities, city)
	delete(g.routes[city], city)
	for key := range g.routes {
		delete(g.routes[key], city)
	}
}

// FindPath знаходить найкоротший шлях і ціну поїздки між двома містами.
func (g *Graph) FindPath(cityA, cityB string) (bool, int) {
	g.mu.RLock()
	defer g.mu.RUnlock()
	visited := make(map[string]bool)
	return g.dfs(cityA, cityB, visited)
}

// dfs - це рекурсивна функція для пошуку шляху між містами у глибину.
func (g *Graph) dfs(cityA, cityB string, visited map[string]bool) (bool, int) {
	if cityA == cityB {
		return true, 0
	}
	visited[cityA] = true
	minPrice := -1
	found := false
	for city, price := range g.routes[cityA] {
		if !visited[city] {
			if ok, p := g.dfs(city, cityB, visited); ok {
				if minPrice == -1 || price+p < minPrice {
					minPrice = price + p
				}
				found = true
			}
		}
	}
	visited[cityA] = false
	return found, minPrice
}

func main() {
	g := NewGraph()

	g.AddCity("A")
	g.AddCity("B")
	g.AddCity("C")

	g.AddRoute("A", "B", 100)
	g.AddRoute("B", "C", 200)

	g.SetTicketPrice("A", "C", 300)

	found, price := g.FindPath("A", "C")
	fmt.Printf("Route from A to C: Found: %v, Price: %d\n", found, price)

	g.DeleteRoute("A", "B")

	found, price = g.FindPath("A", "B")
	fmt.Printf("Route from A to B after deletion: Found: %v, Price: %d\n", found, price)
}

